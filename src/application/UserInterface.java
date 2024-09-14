package application;

import chess.ChessMatch;

import javax.swing.*;
import java.util.Objects;

public class UserInterface extends JFrame {

    public UserInterface(ChessMatch match, Sizes sizes) {
        super("Chess");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(logoIcon.getImage());

        // Load the pieces images files
        PieceLoader pieceLoader = new PieceLoader(new ImageIcon[sizes.getBOARD_SIZE()][sizes.getBOARD_SIZE()]);

        // Pass the pieces images to the drawer
        PieceDrawer pieceDrawer = new PieceDrawer(pieceLoader.getPiecesIcons(), sizes);

        // Do the connection between the user and the board
        BoardInterface boardInterface = new BoardInterface(pieceDrawer, match, sizes);
        getContentPane().add(boardInterface);

        // Size the window based on its content
        pack();

        // Make the container visible
        setVisible(true);
    }
}
