package controller;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

import javax.swing.*;

public class Movements {

    private final ChessMatch match;

    public Movements(ChessMatch match) {
        this.match = match;
    }

    // Changes the position of a piece and make the capture of an opponent piece
    protected void chessPieceMovement(Position source, Position target) {
        if (!validateMovePosition(source, target)) {
            JOptionPane.showMessageDialog(null, "Target position invalid.",
                    "Position error", JOptionPane.INFORMATION_MESSAGE, null);
            throw new RuntimeException("Invalid move.");
        }

        ChessPiece selectedPiece = (ChessPiece) match.getBoard().getPieceOn(source);

        match.getBoard().removePiece(source);
        match.getBoard().removePiece(target);
        match.getBoard().placePiece(target, selectedPiece);

        if (selectedPiece != null && !match.validatePieceColor(target)) selectedPiece.addMoveCount();

        match.nextTurn();
    }

    // Validates all positions of a movement to ensure it can be done
    protected boolean validateMovePosition(Position source, Position target) {
        boolean[][] possibilities = match.getBoard().getPieceOn(source).possibleMoves();
        possibilities = invertMatrix(possibilities);
        return possibilities[target.getRow()][target.getColumn()];
    }

    // Inverts a matrix columns
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
