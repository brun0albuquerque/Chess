package application;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PieceLoader extends JFrame {

    private ImageIcon[][] piecesIcons;

    public PieceLoader(ImageIcon[][] icons) throws HeadlessException {
        super();
        this.piecesIcons = icons;
        loadInitialPiecesIcons();
        piecesIcons = invertMatrix(piecesIcons);
    }

    public ImageIcon[][] getPiecesIcons() {
        return piecesIcons;
    }

    public void loadInitialPiecesIcons() {

        // Load the piecesImages image files
        ImageIcon whitePawn = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/white_pawn.png")));
        ImageIcon whiteRook = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/white_rook.png")));
        ImageIcon whiteBishop = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/white_bishop.png")));
        ImageIcon whiteKing = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/white_king.png")));
        ImageIcon whiteQueen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/white_queen.png")));
        ImageIcon whiteKnight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/white_knight.png")));

        ImageIcon blackPawn = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/black_pawn.png")));
        ImageIcon blackRook = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/black_rook.png")));
        ImageIcon blackBishop = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/black_bishop.png")));
        ImageIcon blackKing = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/black_king.png")));
        ImageIcon blackQueen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/black_queen.png")));
        ImageIcon blackKnight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/black_knight.png")));

        // Place white piecesImages
        for (int a = 0; a < 8; a++) {
            piecesIcons[6][a] = whitePawn;
        }
        piecesIcons[7][0] = whiteRook;
        piecesIcons[7][1] = whiteKnight;
        piecesIcons[7][2] = whiteBishop;
        piecesIcons[7][3] = whiteKing;
        piecesIcons[7][4] = whiteQueen;
        piecesIcons[7][5] = whiteBishop;
        piecesIcons[7][6] = whiteKnight;
        piecesIcons[7][7] = whiteRook;

        // Place black piecesImages
        for (int a = 0; a < 8; a++) {
            piecesIcons[1][a] = blackPawn;
        }
        piecesIcons[0][0] = blackRook;
        piecesIcons[0][1] = blackKnight;
        piecesIcons[0][2] = blackBishop;
        piecesIcons[0][3] = blackKing;
        piecesIcons[0][4] = blackQueen;
        piecesIcons[0][5] = blackBishop;
        piecesIcons[0][6] = blackKnight;
        piecesIcons[0][7] = blackRook;
    }

    private ImageIcon[][] invertMatrix(ImageIcon[][] matrix) {
        ImageIcon[][] newMatrix = new ImageIcon[8][8];
        for (int a = 7; a >= 0; a--) {
            for (int b = 0; b < 8; b++) {
                newMatrix[7 - a][b] = matrix[b][a];
            }
        }
        return newMatrix;
    }
}
