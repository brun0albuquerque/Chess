package controller;

import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.King;
import pieces.Pawn;
import pieces.Queen;


public class Movements {

    private final ChessMatch match;

    public Movements(ChessMatch match) {
        this.match = match;
    }

    // Changes the position of a piece and make the capture of an opponent piece
    protected boolean pieceMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) match.getBoard().getPieceOn(source);

        if (!validateMovePosition(source, target)) {
            return false;
        }

        piece.addMoveCount();
        match.getBoard().removePiece(source);
        match.getBoard().removePiece(target);
        match.getBoard().placePiece(target, piece);
        match.nextTurn();
        return true;
    }

    // Validates weather the selected position matches with any true position of possibilities
    protected boolean validateMovePosition(Position source, Position target) {
        boolean[][] possibilities;
        Piece piece = match.getBoard().getPieceOn(source);

        if (piece instanceof King) {
            possibilities = ((King) piece).possibleMoves(piece.possibleMoves());
        } else {
            possibilities = match.getBoard().getPieceOn(source).possibleMoves();
        }
        return possibilities[target.getRow()][target.getColumn()];
    }

    // Checks weather the pawn can be promoted or not, by checking its position
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
