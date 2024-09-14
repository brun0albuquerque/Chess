package application;

import chess.ChessMatch;

import javax.swing.*;
import java.util.Objects;

public class UserInterface extends JFrame {

    public UserInterface(ChessMatch match) {
        super("Chess");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(icon.getImage());

        // Load the pieces images files
        PieceLoader loader = new PieceLoader(new ImageIcon[InterfaceSizes.getBOARD_SIZE()][InterfaceSizes.getBOARD_SIZE()]);

        // Pass the pieces images to the drawer
        PieceDrawer drawer = new PieceDrawer(loader.getPiecesIcons());

        // Do the connection between the user and the board
        BoardInterface boardInterface = new BoardInterface(drawer, match);
        getContentPane().add(boardInterface);

        // Size the window based on its content
        pack();

        // Make the container visible
        setVisible(true);
    }
}
