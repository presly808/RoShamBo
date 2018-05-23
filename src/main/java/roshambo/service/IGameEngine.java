package roshambo.service;

import roshambo.model.Figure;
import roshambo.model.Round;

import java.util.List;

/**
 * Created by serhii on 22.05.18.
 */
public interface IGameEngine {

    Round playRound(Figure humanFigure);

    int getHumanScore();

    int getBotSore();

    List<Round> getHistory();

}
