import application.InterfaceException;
import application.Sizes;
import application.UserInterface;
import chess.ChessMatch;

public class Main {
    public static void main(String[] args) {
        try {
            Sizes sizes = new Sizes();
            ChessMatch match = new ChessMatch(sizes);
            UserInterface userInterface = new UserInterface(match, sizes);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (InterfaceException e) {
            throw new InterfaceException("Interface exception." + e.getMessage());
        }
    }
}
