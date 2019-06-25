package roshambo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import roshambo.model.Figure;
import roshambo.model.PlayerType;
import roshambo.model.Round;
import roshambo.service.GameEngine;
import roshambo.utils.GameUtils;

import java.util.List;

/**
 * Created by serhii on 22.05.18.
 */
public class GameEngineTest {

    private static final int COUNT = 100_000;
    private GameEngine gameEngine;

    @Before
    public void before() {
        gameEngine = new GameEngine();
    }

    @After
    public void after() {
        gameEngine = null;
    }

    @Test
    public void randomFigureEachTime() throws Exception {
        for (int i = 0; i < COUNT; i++) {
            Figure[] values = Figure.values();
            gameEngine.playRound(values[(int) (Math.random() * values.length)]);
        }

        System.out.println(GameUtils.getFormattedResult(gameEngine));
    }

    @Test
    public void useConditionIfWinAndLose() throws Exception {
        List<Round> roundList = gameEngine.getHistory();
        roundList.add(new Round(Figure.ROCK, Figure.SCISSORS, PlayerType.HUMAN));
        for (int i = 0; i < COUNT; i++) {
            Figure humanFigure;

            Round lastRound = roundList.get(roundList.size() - 1);

            if (lastRound.getWinner() == PlayerType.HUMAN) {
                humanFigure = lastRound.getHumanFigure();
            } else {
                humanFigure = GameUtils.getWinFigure(lastRound.getBotFigure());
            }

            gameEngine.playRound(humanFigure);
        }
        System.out.println(GameUtils.getFormattedResult(gameEngine));

        Assert.assertTrue(gameEngine.getBotSore() > gameEngine.getHumanScore());
    }

    @Test
    public void useReversedPrevBotFigure() throws Exception {
        List<Round> roundList = gameEngine.getHistory();
        roundList.add(new Round(Figure.ROCK, Figure.SCISSORS, PlayerType.HUMAN));
        for (int i = 0; i < COUNT; i++) {
            Figure humanFigure;

            Round lastRound = roundList.get(roundList.size() - 1);

            humanFigure = GameUtils.getWinFigure(lastRound.getBotFigure());

            gameEngine.playRound(humanFigure);

        }
        System.out.println(GameUtils.getFormattedResult(gameEngine));
        Assert.assertTrue(gameEngine.getBotSore() > gameEngine.getHumanScore());
    }

    @Test
    public void usePrevBotFigure() throws Exception {
        List<Round> roundList = gameEngine.getHistory();
        roundList.add(new Round(Figure.ROCK, Figure.SCISSORS, PlayerType.HUMAN));
        for (int i = 0; i < COUNT; i++) {
            Round lastRound = roundList.get(roundList.size() - 1);
            gameEngine.playRound(lastRound.getBotFigure());
        }
        System.out.println(GameUtils.getFormattedResult(gameEngine));
    }

    @Test
    public void variousAlgoInside() throws Exception {
        List<Round> roundList = gameEngine.getHistory();
        roundList.add(new Round(Figure.ROCK, Figure.SCISSORS, PlayerType.HUMAN));

        for (int i = 0; i < COUNT; i++) {
            Figure[] values = Figure.values();
            gameEngine.playRound(values[i % values.length]);
        }

        for (int i = 0; i < COUNT; i++) {
            Figure[] values = Figure.values();
            gameEngine.playRound(values[i % 2]);
        }

        for (int i = 0; i < COUNT; i++) {
            Figure[] values = Figure.values();
            gameEngine.playRound(values[(int)(Math.random() * 3)]);
        }


        System.out.println(GameUtils.getFormattedResult(gameEngine));
        Assert.assertTrue(gameEngine.getBotSore() > gameEngine.getHumanScore());
    }

}