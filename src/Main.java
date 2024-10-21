import chess.ChessMatch;
import controller.BoardController;
import application.ChessInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            ChessMatch match = new ChessMatch();
            BoardController controller = new BoardController(match);
            ChessInterface chessInterface = new ChessInterface(match, controller);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Null pointer: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Illegal argument: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }
    }
}
