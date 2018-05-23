package roshambo.model;

/**
 * Created by serhii on 22.05.18.
 */
public class Round {

    private Figure humanFigure;
    private Figure botFigure;
    private PlayerType winner;

    public Round() {
    }

    public Round(Figure humanFigure,
                 Figure botFigure,
                 PlayerType winner) {
        this.humanFigure = humanFigure;
        this.botFigure = botFigure;
        this.winner = winner;
    }

    public Figure getHumanFigure() {
        return humanFigure;
    }

    public void setHumanFigure(Figure humanFigure) {
        this.humanFigure = humanFigure;
    }

    public Figure getBotFigure() {
        return botFigure;
    }

    public void setBotFigure(Figure botFigure) {
        this.botFigure = botFigure;
    }

    public PlayerType getWinner() {
        return winner;
    }

    public void setWinner(PlayerType winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Round{" +
                "humanFigure=" + humanFigure +
                ", botFigure=" + botFigure +
                ", winner=" + winner +
                '}';
    }
}
