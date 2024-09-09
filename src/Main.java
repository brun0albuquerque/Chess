import application.InterfaceException;
import application.UserInterface;
import chess.ChessMatch;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Resource path: " + Main.class.getResource("/resources/board.png"));
            ChessMatch chessMatch = new ChessMatch();
            UserInterface userInterface = new UserInterface();
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (InterfaceException e) {
            throw new InterfaceException("Interface exception." + e.getMessage());
        }
    }
}
