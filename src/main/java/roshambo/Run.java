package roshambo;

import roshambo.model.Figure;
import roshambo.model.Round;
import roshambo.service.GameEngine;
import roshambo.utils.GameUtils;

import java.util.Scanner;

/**
 * Created by serhii on 22.05.18.
 */
public class Run {

    public static void main(String[] args) {

        GameEngine gameEngine = new GameEngine();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.printf("Choose figure's num:\n%s(%s)\n%s(%s)\n%s(%s)\nExit(-1)\n",
                    Figure.ROCK, Figure.ROCK.ordinal(),
                    Figure.PAPER, Figure.PAPER.ordinal(),
                    Figure.SCISSORS, Figure.SCISSORS.ordinal());

            int choice = scanner.nextInt();

            if (choice == -1) {
                return;
            }

            Round round = gameEngine.playRound(Figure.values()[choice]);

            System.out.println(round);
            System.out.println(GameUtils.getFormattedResult(gameEngine));
        }

    }

}
