package controller;

import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.King;
import pieces.Rook;


public class Movements {

    private final ChessMatch match;

    public Movements(ChessMatch match) {
        this.match = match;
    }

    /* Changes the position of a piece and make the capture of an opponent piece. */
    protected void pieceMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) match.getBoard().getPieceOn(source); /* Get the piece from the board. */

        /* Remove both source and target pieces from the board. */
        match.getBoard().removePiece(source);
        match.getBoard().removePiece(target);

        /* Place the source piece on target position. */
        match.getBoard().placePiece(target, piece);

        /* Add the move counter by one. */
        piece.addMoveCount();

        /* Change to the next turn. */
        match.nextTurn();
    }

    /*
     * Validates weather the selected position matches any true position of possibilities.
     * It will calculate all possibilities for each piece on the board.
     * If the piece it's the king, then only permit safe moves by calling a method from class King.
     * Returns true only if the selected square has a true value in the matrix.
     */
    public boolean validateMovePosition(Position source, Position target) {
        boolean[][] possibilities;
        Piece piece = match.getBoard().getPieceOn(source);

        if (piece instanceof King)
            possibilities = ((King) piece).possibleMoves();
        else
            possibilities = piece.possibleMoves(true);

        return possibilities[target.getRow()][target.getColumn()];
    }

    /* Make the castling especial move. */
    public void castlingMove(Position kingPosition, Position rookPosition) {
        King king = (King) match.getBoard().getPieceOn(kingPosition);
        Rook rook = (Rook) match.getBoard().getPieceOn(rookPosition);

        /* Remove the king and rook from the board. */
        match.getBoard().removePiece(kingPosition);
        match.getBoard().removePiece(rookPosition);

        /* When the king's position is higher than the rook's position (further away), make the long castling,
        otherwise make the short castling. */
        int kingRow = (kingPosition.getRow() > rookPosition.getRow()) ? kingPosition.getRow() - 2 : kingPosition.getRow() + 2;
        int rookRow = (kingPosition.getRow() > rookPosition.getRow()) ? rookPosition.getRow() + 2 : rookPosition.getRow() - 3;
        Position kingAfterCastling = new Position(kingRow, kingPosition.getColumn());
        Position rookAfterCastling = new Position(rookRow, rookPosition.getColumn());

        /* Place the pieces in their new position. */
        match.getBoard().placePiece(kingAfterCastling, king);
        match.getBoard().placePiece(rookAfterCastling, rook);

        /* Add the move counter by one for both pieces. */
        king.addMoveCount();
        rook.addMoveCount();

        /* Change to the next turn. */
        match.nextTurn();
    }
}
