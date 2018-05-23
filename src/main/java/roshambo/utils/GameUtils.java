package roshambo.utils;

import roshambo.model.Figure;
import roshambo.model.PlayerType;
import roshambo.model.Result;
import roshambo.model.Round;
import roshambo.service.GameEngine;

import java.util.List;

/**
 * Created by serhii on 22.05.18.
 */
public class GameUtils {

    public static Figure getWinFigure(Figure figure) {

        if (figure == Figure.SCISSORS) {
            return Figure.ROCK;
        }

        if (figure == Figure.ROCK) {
            return Figure.PAPER;
        }

        return Figure.SCISSORS;
    }

    public static PlayerType defineWinner(Figure humanFigure, Figure botFigure) {

        if (didFirstWin(botFigure, humanFigure)) return PlayerType.BOT;

        if (didFirstWin(humanFigure, botFigure)) return PlayerType.HUMAN;

        // null if figures were the same (DRAW case)
        return null;
    }

    public static boolean didFirstWin(Figure player1, Figure player2) {
        if (player1 == Figure.ROCK && player2 == Figure.SCISSORS) {
            return true;
        }

        if (player1 == Figure.PAPER && player2 == Figure.ROCK) {
            return true;
        }

        if (player1 == Figure.SCISSORS && player2 == Figure.PAPER) {
            return true;
        }

        return false;
    }

    public static Figure findFigureByShiftFromPrev(Figure figure, int shiftFromPrevResult) {
        Figure[] values = Figure.values();
        return values[(figure.ordinal() + shiftFromPrevResult) % values.length];
    }

    public static int findMaxValueIndex(Double[] doubles) {
        int maxIndex = 0;
        for (int i = 0; i < doubles.length; i++) {
            if (doubles[i] > doubles[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static Result getHumanResult(PlayerType prevWinner) {
        Result humanPrevResult;
        if (prevWinner == PlayerType.HUMAN) {
            humanPrevResult = Result.WIN;
        } else if (prevWinner == PlayerType.BOT) {
            humanPrevResult = Result.LOSE;
        } else {
            humanPrevResult = Result.DRAW;
        }
        return humanPrevResult;
    }

    public static String getFormattedResult(GameEngine gameEngine) {
        List<Round> roundList = gameEngine.getHistory();
        return String.format("Human's score %s(%.0f%%), Bot's score %s(%.0f%%)\n",
                gameEngine.getHumanScore(),
                gameEngine.getHumanScore() * 1.0 / roundList.size() * 100,
                gameEngine.getBotSore(),
                gameEngine.getBotSore() * 1.0 / roundList.size() * 100);
    }
}
