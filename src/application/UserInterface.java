package application;

import javax.swing.*;
import java.util.Objects;

import static application.Sizes.BOARD_SIZE;
import static application.Sizes.INIT_DIMENSION;

public class UserInterface extends JFrame {

    public UserInterface() {
        super();
        setTitle("Chess");
        setSize(INIT_DIMENSION, INIT_DIMENSION);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        System.out.println("Logo icon: " + logoIcon);
        setIconImage(logoIcon.getImage());

        // Load the pieces images files
        PieceLoader pieceLoader = new PieceLoader(new ImageIcon[BOARD_SIZE][BOARD_SIZE]);

        /* Hold the method that draw the pieces on the board when PaintComponent is called
        and create the matrix for the pieces images */
        PieceDrawer pieceDrawer = new PieceDrawer(pieceLoader.getPiecesImages());

        // Do the connection between the user and the board
        BoardInterface boardInterface = new BoardInterface(pieceDrawer);
        getContentPane().add(boardInterface);

        // Make the container visible
        setVisible(true);
    }
}
