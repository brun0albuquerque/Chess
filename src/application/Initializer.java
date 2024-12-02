package application;

import chess.ChessMatch;
import controller.InterfaceController;
import controller.InputController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Initializer extends JFrame {

    public Initializer(InputController action, ChessMatch match) throws HeadlessException {
        super("Chess");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617 */
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(icon.getImage());

        /* Load the pieces images files. */
        Loader loader = new Loader(new ImageIcon[Sizes.getBOARD_SIZE()][Sizes.getBOARD_SIZE()]);

        /* Pass the pieces images to the drawer. */
        Drawer drawer = new Drawer(loader.getPiecesIcons());

        /* Make the connection between the user and the board. */
        InterfaceController anInterfaceController = new InterfaceController(action, drawer, match);
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
