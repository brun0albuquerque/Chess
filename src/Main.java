import application.ChessInterface;
import application.InterfaceException;
import chess.ChessMatch;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            ChessMatch match = new ChessMatch();
            ChessInterface chessInterface = new ChessInterface(match);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Files could not be loaded, the game will close.",
                    "Error", JOptionPane.INFORMATION_MESSAGE, null);
            System.exit(1);
        } catch (InterfaceException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Error", JOptionPane.INFORMATION_MESSAGE, null);
            System.exit(1);
        }
    }
}
