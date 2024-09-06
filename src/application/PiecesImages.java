package application;

import javax.swing.*;
import java.util.Objects;

public class PiecesImages extends JFrame {

    private final ImageIcon[][] pieces = new ImageIcon[8][8];

    public PiecesImages() {
        loadInitialPieces();
    }

    private void loadInitialPieces() {

        // Load the pieces image files
        ImageIcon whitePawn = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/white_pawn.png")));
        ImageIcon whiteRook = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/white_rook.png")));
        ImageIcon whiteBishop = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/white_bishop.png")));
        ImageIcon whiteKing = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/white_king.png")));
        ImageIcon whiteQueen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/white_queen.png")));
        ImageIcon whiteKnight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/white_knight.png")));

        ImageIcon blackPawn = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/black_pawn.png")));
        ImageIcon blackRook = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/black_rook.png")));
        ImageIcon blackBishop = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/black_bishop.png")));
        ImageIcon blackKing = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/black_king.png")));
        ImageIcon blackQueen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/black_queen.png")));
        ImageIcon blackKnight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/chess_pieces/black_knight.png")));

        // Place white pieces
        for (int a = 0; a < 8; a++) {
            pieces[6][a] = whitePawn;
        }
        pieces[7][0] = whiteRook;
        pieces[7][1] = whiteKnight;
        pieces[7][2] = whiteBishop;
        pieces[7][3] = whiteQueen;
        pieces[7][4] = whiteKing;
        pieces[7][5] = whiteBishop;
        pieces[7][6] = whiteKnight;
        pieces[7][7] = whiteRook;

        // Place black pieces
        for (int a = 0; a < 8; a++) {
            pieces[1][a] = blackPawn;
        }
        pieces[0][0] = blackRook;
        pieces[0][1] = blackKnight;
        pieces[0][2] = blackBishop;
        pieces[0][3] = blackQueen;
        pieces[0][4] = blackKing;
        pieces[0][5] = blackBishop;
        pieces[0][6] = blackKnight;
        pieces[0][7] = blackRook;
    }


    public ImageIcon[][] getPieces() {
        if (pieces[0][0] == null) {
            throw new InterfaceException("No pieces.");
        }
        return pieces;
    }
}
