import chess.ChessMatch;
import controller.MouseActions;
import application.ChessInterface;
import controller.Moviments;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            ChessMatch match = new ChessMatch();
            Moviments moviments = new Moviments(match);
            MouseActions controller = new MouseActions(moviments, match);
            ChessInterface chessInterface = new ChessInterface(controller, match);
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
