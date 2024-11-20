import chess.ChessMatch;
import controller.MouseActions;
import application.ChessInterface;
import controller.Movements;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            ChessMatch match = new ChessMatch();
            Movements movements = new Movements(match);
            MouseActions actions = new MouseActions(movements, match);
            ChessInterface chessInterface = new ChessInterface(actions, match);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Null pointer: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Illegal argument: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(null, "Illegal state: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }
    }
}
