package application;

import chess.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UserInterface extends JFrame {

    private Dimension resolution;
    private BoardPanel boardPanel;

    public UserInterface(int width, int height) {
        super();
        setTitle("Chess");
        setResolution(width, height);
        setSize(getResolution());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(logoIcon.getImage());

        // Creates the board and add to the frame and initialize the game pieces (before initialize BoardPanel)
        BoardPanel boardPanel = new BoardPanel(new PrintPieces());
        getContentPane().add(boardPanel);

        setVisible(true);
    }

    private Dimension getResolution() {
        return resolution;
    }

    public BoardPanel getBoardPanel() {
        return this.boardPanel;
    }

    // Convert the resolution from type Integer to Dimension
    private void setResolution(int width, int height) {
        this.resolution = new Dimension(width, height);
    }

    public void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
    }
}
