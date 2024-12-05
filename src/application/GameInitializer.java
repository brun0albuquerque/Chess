package application;

import chess.ChessMatch;
import controller.GameController;
import controller.GameInterface;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameInitializer extends JFrame {

    public GameInitializer() throws HeadlessException {
        super("Chess");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessMatch chessMatch = new ChessMatch();

        /* Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617 */
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/resources/chess_logo.png")));
        setIconImage(icon.getImage());

        /* Load the pieces images files. */
        GameInterface gameInterface = createGameController(chessMatch);
        getContentPane().add(gameInterface);

        /* Size the window based on its content. */
        pack();
        centralizeGameWindow();

        /* Make the container visible. */
        setVisible(true);
    }

    private GameInterface createGameController(ChessMatch chessMatch) {
        PiecesLoader piecesLoader = new PiecesLoader(
                new ImageIcon[Sizes.getBOARD_SIZE()][Sizes.getBOARD_SIZE()]
        );

        /* Pass the pieces images to the drawer. */
        GameDrawer gameDrawer = new GameDrawer(piecesLoader.getPiecesIcons());

        GameController gameController = new GameController(chessMatch, gameDrawer);

        /* Make the connection between the user and the board. */
        return new GameInterface(chessMatch, gameDrawer, gameController);
    }

    private void centralizeGameWindow() {
        /* Centralize the window manually. */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
}
