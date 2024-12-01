package controller;

import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.King;
import pieces.Rook;

public class PlayerAction {

    private final ChessMatch match;

    protected static Integer aX = null;
    protected static Integer aY = null;
    protected static Integer bX = null;
    protected static Integer bY = null;

    public PlayerAction(ChessMatch match) {
        this.match = match;
    }

    /* Handles the player interactions on the board. */
    protected void handlePieceSelection(int x, int y) {
        Position position = new Position(x, y);
        ChessPiece selectedPiece = (ChessPiece) match.getBoard().getPieceOn(position);

        if (isAllCoordinatesNull() && selectedPiece != null && match.validatePieceColor(position)) {

            if (aX != null && aY != null && match.validateCastlingPieces(
                    new Position(PlayerAction.aX, PlayerAction.aY), position)) {
                bX = x;
                bY = y;
                return;
            }
            aX = x;
            aY = y;

        } else if (aX != null && aY != null) {
            bX = x;
            bY = y;
        }
    }

    /* Checks if any coordinate is null. */
    protected boolean isAllCoordinatesNull() {
        return aX == null || aY == null || bX == null || bY == null;
    }

    /* Sets all coordinates null. */
    protected void cleanAllCoordinates() {
        aX = null;
        aY = null;
        bX = null;
        bY = null;
    }

    /* Perform the logic move on the board. */
    protected boolean validateLogicMove(ChessMatch match) {
        if (match.getPieces()[aX][aY] == null)
            return false;

        Position source = new Position(aX, aY);
        Position target = new Position(bX, bY);

        if (!match.validateSourcePosition(source) && !match.validateTargetPosition(target))
            return false;

        /* Validate if the move can be done checking the target position on the board.
         * If the piece is the king, it only allows the move to safe squares. */
        if (!validateMoveExecution(source, target)) {
            cleanAllCoordinates();
            return false;
        }
        return true;
    }

    protected void executeMove(Position source, Position target) {
        /* Validate the castling move before make the move. */
        if (match.validateCastlingPieces(source, target) && match.validateCastlingMove(source, target)) {
            executeCastlingMove(source, target);
            return;
        }

        /* If the movement isn't an especial move, make a normal move. */
        executePieceMove(source, target);
    }

    /* Changes the position of a piece and make the capture of an opponent piece. */
    protected void executePieceMove(Position source, Position target) {
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
     * Any move can only be executed if this returns true.
     */
    private boolean validateMoveExecution(Position source, Position target) {
        boolean[][] possibilities;
        Piece piece = match.getBoard().getPieceOn(source);

        if (piece instanceof King)
            possibilities = ((King) piece).possibleMoves();
        else
            possibilities = piece.possibleMoves(true);

        return possibilities[target.getRow()][target.getColumn()];
    }

    /* Make the castling especial move. */
    private void executeCastlingMove(Position kingPosition, Position rookPosition) {
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

        /* Change to the next turn. */
        match.nextTurn();
    }
}