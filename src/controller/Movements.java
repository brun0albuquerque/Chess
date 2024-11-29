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

    /* Changes the position of a piece and make the capture of an opponent piece. */
    protected boolean pieceMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) match.getBoard().getPieceOn(source); /* Get the piece from the board. */

        /* Validate if the move can be done checking the target position on the board.
        If the piece is the king, only allows the move to safe squares. */
        if (!validateMovePosition(source, target)) return false;

        /* Remove both source and target pieces from the board. */
        match.getBoard().removePiece(source);
        match.getBoard().removePiece(target);

        /* Place the source piece on target position. */
        match.getBoard().placePiece(target, piece);

        /* Add the piece move counter by one. */
        piece.addMoveCount();

        /* Change to the next turn. */
        match.nextTurn();
        return true;
    }

    /*
     * Validates weather the selected position matches with any true position of possibilities. It will calculate
     * all possibilities for every piece on the board. If the piece it's the king, then only permit safe moves by
     * calling a method from King. Only returns true if the selected square has a true value in the matrix.
     */
    private boolean validateMovePosition(Position source, Position target) {
        boolean[][] possibilities;
        Piece piece = match.getBoard().getPieceOn(source);

        if (piece instanceof King) {
            possibilities = ((King) piece).possibleMoves();
        } else {
            possibilities = piece.possibleMoves(true);
        }
        return possibilities[target.getRow()][target.getColumn()];
    }

    /* Checks weather the pawn can be promoted or not, by checking its position. Pawns always be promoted to Queens. */
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
