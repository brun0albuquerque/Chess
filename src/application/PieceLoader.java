package application;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PieceLoader extends JFrame {

    private final ImageIcon[][] piecesImages;

    public PieceLoader(ImageIcon[][] piecesImages) throws HeadlessException, NullPointerException {
        super();
        this.piecesImages = piecesImages;
        loadInitialPiecesIcons();
    }

    public ImageIcon[][] getPiecesImages() {
        return piecesImages;
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
            piecesImages[6][a] = whitePawn;
        }
        piecesImages[7][0] = whiteRook;
        piecesImages[7][1] = whiteKnight;
        piecesImages[7][2] = whiteBishop;
        piecesImages[7][3] = whiteQueen;
        piecesImages[7][4] = whiteKing;
        piecesImages[7][5] = whiteBishop;
        piecesImages[7][6] = whiteKnight;
        piecesImages[7][7] = whiteRook;

        // Place black piecesImages
        for (int a = 0; a < 8; a++) {
            piecesImages[1][a] = blackPawn;
        }
        piecesImages[0][0] = blackRook;
        piecesImages[0][1] = blackKnight;
        piecesImages[0][2] = blackBishop;
        piecesImages[0][3] = blackQueen;
        piecesImages[0][4] = blackKing;
        piecesImages[0][5] = blackBishop;
        piecesImages[0][6] = blackKnight;
        piecesImages[0][7] = blackRook;
    }
}
