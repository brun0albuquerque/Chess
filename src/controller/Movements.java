package controller;

import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Movements {

    private final ChessMatch match;
    private List<Piece> boardPieces;
    private List<Piece> capturedPieces;

    public Movements(ChessMatch match) {
        this.match = match;
        this.boardPieces = match.getBoardPieces();
        this.capturedPieces = new ArrayList<>();
    }

    // Change the position of a piece and make the capture of an opponent piece
    protected void chessPieceMovement(Position source, Position target) {
        if (!validateMovePosition(source, target)) {
            JOptionPane.showMessageDialog(null, "Target position invalid.",
                    "Position error", JOptionPane.INFORMATION_MESSAGE, null);
            throw new RuntimeException("Invalid move.");
        }

        ChessPiece selectedPiece = (ChessPiece) match.getBoard().removePiece(source);
        Piece capturedPiece = match.getBoard().removePiece(target);

        if (capturedPiece != null) {
            boardPieces.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        if (selectedPiece != null) selectedPiece.addMoveCount();

        match.getBoard().placePiece(target, selectedPiece);
        match.nextTurn();
    }

    protected boolean validateMovePosition(Position source, Position target) {
        boolean[][] possibilities = match.getBoard().getPieceOn(source).possibleMoves();
        possibilities = invertMatrix(possibilities);
        System.out.println("Possibilities: " + possibilities[target.getRow()][target.getColumn()] + ", " + target);
        return possibilities[target.getRow()][target.getColumn()];
    }

    private boolean[][] invertMatrix(boolean[][] matrix) {
        boolean[][] newMatrix = new boolean[8][8];

        for (int a = 0; a <= 7; a++) {
            for (int b = 0; b <= 7; b++) {
                newMatrix[a][b] = matrix[a][7 - b];
            }
        }
        return newMatrix;
    }
}
