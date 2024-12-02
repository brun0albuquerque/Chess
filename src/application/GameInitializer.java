package application;

import chess.ChessMatch;
import controller.InterfaceController;
import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameInitializer extends JFrame {

    public GameInitializer(GameController action, ChessMatch match) throws HeadlessException {
        super("Chess");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617 */
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(icon.getImage());

        /* Load the pieces images files. */
        PiecesLoader piecesLoader = new PiecesLoader(new ImageIcon[FrameSizes.getBOARD_SIZE()][FrameSizes.getBOARD_SIZE()]);

        /* Pass the pieces images to the drawer. */
        GameDrawer gameDrawer = new GameDrawer(piecesLoader.getPiecesIcons());

        /* Make the connection between the user and the board. */
        InterfaceController anInterfaceController = new InterfaceController(action, gameDrawer, match);
        getContentPane().add(anInterfaceController);

        /* Size the window based on its content. */
        pack();

        /* Centralize the window manually. */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        /* Make the container visible. */
        setVisible(true);
    }
}
