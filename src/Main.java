import application.InterfaceException;
import application.InterfaceSizes;
import application.UserInterface;
import chess.ChessMatch;

public class Main {
    public static void main(String[] args) {
        try {
            InterfaceSizes sizes = InterfaceSizes.SMALL;
            ChessMatch match = new ChessMatch();
            UserInterface userInterface = new UserInterface(match);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (InterfaceException e) {
            throw new InterfaceException("Interface exception." + e.getMessage());
        }
    }
}
