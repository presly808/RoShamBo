package roshambo.service;

import roshambo.model.Figure;
import roshambo.model.PlayerType;
import roshambo.model.Result;
import roshambo.model.Round;
import roshambo.utils.GameUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by serhii on 22.05.18.
 */
public class GameEngine implements IGameEngine {

    private static final int COUNT_OF_FIGURES = 3;
    private static final double COEFF_FOR_MULT = 0.9;

    private List<Round> history;
    private Map<Result, Double[]> weightsGroupedByResult;

    private int botSore = 0;
    private int humanScore = 0;

    public GameEngine() {

        weightsGroupedByResult = new HashMap<>();
        weightsGroupedByResult.put(Result.WIN, new Double[]{0.0, 0.0, 0.0});
        weightsGroupedByResult.put(Result.LOSE, new Double[]{0.0, 0.0, 0.0});
        weightsGroupedByResult.put(Result.DRAW, new Double[]{0.0, 0.0, 0.0});

        history = new ArrayList<>();
    }

    @Override
    public Round playRound(Figure humanFigure) {

        Figure userPredictedFigure = predictFigure();
        Figure botPredictedFigure = GameUtils.getWinFigure(userPredictedFigure);

        PlayerType winner = GameUtils.defineWinner(humanFigure, botPredictedFigure);

        if (winner == PlayerType.HUMAN) {
            humanScore += 1;
        } else if (winner == PlayerType.BOT) {
            botSore += 1;
        }

        Round newRound = new Round(humanFigure, botPredictedFigure, winner);

        saveRound(newRound);

        return newRound;
    }

    @Override
    public int getHumanScore() {
        return humanScore;
    }

    @Override
    public int getBotSore() {
        return botSore;
    }

    @Override
    public List<Round> getHistory() {
        return history;
    }

    private void saveRound(Round round) {
        history.add(round);
        if (history.size() > 1) {
            changeWeights();
        }
    }

    private void changeWeights() {

        Figure lastHumanFigure = history.get(history.size() - 1).getHumanFigure();

        Figure prevHumanFigure = history.size() == 1 ?
                lastHumanFigure : history.get(history.size() - 2).getHumanFigure();

        int shift = lastHumanFigure.ordinal() - prevHumanFigure.ordinal();

        // we add 3 to keep the shift positive and to be in range 0,1,2
        // but in some cases we can get minus value by subtraction
        shift = shift < 0 ? shift + COUNT_OF_FIGURES : shift;

        Result prevHumanResult = GameUtils.getHumanResult(history.get(history.size() - 2).getWinner());

        // mult all weight by coefficient, it will help to adapt the bot fast, if user changes his strategy
        weightsGroupedByResult.forEach((key, doubles) -> {
            for (int i = 0; i < doubles.length; i++) {
                doubles[i] *= COEFF_FOR_MULT;
            }
        });

        // add one to remember user's shift based on prev result
        weightsGroupedByResult.get(prevHumanResult)[shift] += 1;
    }

    private Figure predictFigure() {

        if (history.size() == 0) {
            int randomFigureIndex = (int) (Math.random() * COUNT_OF_FIGURES);
            return Figure.values()[randomFigureIndex];
        }

        Round lastRound = history.get(history.size() - 1);
        PlayerType prevWinner = lastRound.getWinner();
        Result humanPrevResult = GameUtils.getHumanResult(prevWinner);

        Double[] doubles = weightsGroupedByResult.get(humanPrevResult);

        int userPredictedShiftFromPrevResult = GameUtils.findMaxValueIndex(doubles);

        return GameUtils.findFigureByShiftFromPrev(lastRound.getHumanFigure(), userPredictedShiftFromPrevResult);
    }
}
