import application.ChessInterface;
import chess.ChessMatch;
import controller.MouseActions;
import controller.Movements;

import javax.swing.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            ChessMatch match = new ChessMatch();
            Movements movements = new Movements(match);
            MouseActions actions = new MouseActions(movements, match);
            ChessInterface chessInterface = new ChessInterface(actions, match);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.out.printf(Arrays.toString(e.getStackTrace()));
            System.exit(1);
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }
    }
}
