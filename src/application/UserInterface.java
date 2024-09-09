package application;

import javax.swing.*;
import java.util.Objects;

public class UserInterface extends JFrame {

    public UserInterface(Sizes sizes) {
        super();
        setTitle("Chess");
//        setSize(sizes.getDIMENSION(), sizes.getDIMENSION());
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(logoIcon.getImage());

        // Load the pieces images files
        PieceLoader pieceLoader = new PieceLoader(new ImageIcon[sizes.getBOARD_SIZE()][sizes.getBOARD_SIZE()]);

        // Pass the pieces images to the drawer
        PieceDrawer pieceDrawer = new PieceDrawer(pieceLoader.getPiecesImages(), sizes);

        // Do the connection between the user and the board
        BoardInterface boardInterface = new BoardInterface(pieceDrawer, sizes);
        getContentPane().add(boardInterface);

        // Size the window based on its content
        pack();

        // Make the container visible
        setVisible(true);
    }
}
