package application;

import chess.ChessMatch;
import controller.Interface;
import controller.MouseActions;

import javax.swing.*;
import java.util.Objects;

public class ChessInterface extends JFrame {

    public ChessInterface(MouseActions actions, ChessMatch match) {
        super("Chess");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(icon.getImage());

        // Load the pieces images files
        PieceLoader loader = new PieceLoader(new ImageIcon[Sizes.getBOARD_SIZE()][Sizes.getBOARD_SIZE()]);

        // Pass the pieces images to the drawer
        PieceDrawer drawer = new PieceDrawer(loader.getPiecesIcons());

        // Do the connection between the user and the board
        Interface anInterface = new Interface(actions, drawer, match);
        getContentPane().add(anInterface);

        // Size the window based on its content
        pack();

        // Make the container visible
        setVisible(true);
    }
}
