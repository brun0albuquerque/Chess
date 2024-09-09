import application.InterfaceException;
import application.Sizes;
import application.UserInterface;
import chess.ChessMatch;

public class Main {
    public static void main(String[] args) {
        try {
            ChessMatch chessMatch = new ChessMatch();
            Sizes sizes = new Sizes();
            sizes.setSmallSizes();
            System.out.println(sizes.getTILE_SIZE() + " " + sizes.getPIECE_SIZE() + " " + sizes.getDIMENSION());
            UserInterface userInterface = new UserInterface(sizes);

        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (InterfaceException e) {
            throw new InterfaceException("Interface exception." + e.getMessage());
        }
    }
}
