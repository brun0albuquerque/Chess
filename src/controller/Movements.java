package controller;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.Pawn;
import pieces.Queen;


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

    public boolean checkPawnPromotion(Position position) {
        ChessPiece piece = (ChessPiece) match.getBoard().getPieceOn(position);

        if (piece instanceof Pawn && piece.getPosition().getColumn() == 0
                || piece instanceof Pawn && piece.getPosition().getColumn() == 7) {
            match.getBoard().removePiece(position);
            match.getBoard().placePiece(position, new Queen(match.getBoard(), piece.getColor()));
            return true;
        }
        return false;
    }
}
