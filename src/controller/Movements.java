package controller;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;


public class Movements {

    private final ChessMatch match;

    public Movements(ChessMatch match) {
        this.match = match;
    }

    // Changes the position of a piece and make the capture of an opponent piece
    protected boolean chessPieceMovement(Position source, Position target) {
        if (!validateMovePosition(source, target)) {
            return false;
        }

        ChessPiece sourcePiece = (ChessPiece) match.getBoard().getPieceOn(source);
        sourcePiece.addMoveCount();
        match.getBoard().removePiece(source);
        match.getBoard().removePiece(target);
        match.getBoard().placePiece(target, sourcePiece);
        match.nextTurn();
        return true;
    }

    // Validates all positions of a movement to ensure it can be done
    protected boolean validateMovePosition(Position source, Position target) {
        boolean[][] possibilities = match.getBoard().getPieceOn(source).possibleMoves();
        return possibilities[target.getRow()][target.getColumn()];
    }
}
