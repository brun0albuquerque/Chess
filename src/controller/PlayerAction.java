package controller;

import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.King;
import pieces.Rook;

import javax.swing.*;

public class PlayerAction {

    private final ChessMatch match;

    protected static Integer aX = null;
    protected static Integer aY = null;
    protected static Integer bX = null;
    protected static Integer bY = null;

    protected static Position source = null;
    protected static Position target = null;


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
                target = new Position(PlayerAction.bX, PlayerAction.bY);
                return;
            }
            aX = x;
            aY = y;
            source = new Position(PlayerAction.aX, PlayerAction.aY);

        } else if (aX != null && aY != null) {
            bX = x;
            bY = y;
            target = new Position(PlayerAction.bX, PlayerAction.bY);
        }
    }

    /* Checks if any coordinate is null. */
    protected boolean isAllCoordinatesNull() {
        return aX == null || aY == null || bX == null || bY == null || source == null || target == null;
    }

    /* Sets all coordinates null. */
    protected void cleanAllCoordinates() {
        source = null;
        target = null;
        aX = null;
        aY = null;
        bX = null;
        bY = null;
    }

    /* Perform the logic move on the board. */
    protected boolean validateLogicMove() {
        ChessMatch.kingCheck = match.isKingInCheck(source, target);

        King king = (King) match.getBoard().getPieceOn(match.getBoard().findKingOnBoard(match.getPlayerColor()));
        if (ChessMatch.kingCheck && !king.hasAnyValidMove() && !playerHasAnyValidMove()) {
            JOptionPane.showMessageDialog(null, "Checkmate. Game over.",
                    "Chess", JOptionPane.INFORMATION_MESSAGE, null);
            System.exit(0);
        } else if (!ChessMatch.kingCheck && !king.hasAnyValidMove() && !playerHasAnyValidMove()) {
            JOptionPane.showMessageDialog(
                    null,
                    "The " + match.getPlayerColor().toString() + " pieces has no valid moves, it's a draw.",
                    "Chess",JOptionPane.INFORMATION_MESSAGE,null);
            System.exit(0);
        }

        if (!match.validateSourcePosition(source) && !match.validateTargetPosition(target))
            return false;

        /* Validate if the move can be done checking the target position on the board.
         * If the piece is the king, it only allows the move to safe squares. */
        if (!validateMoveExecution(source, target)) {
            cleanAllCoordinates();
            return false;
        }
        return !ChessMatch.kingCheck;
    }

    protected void executeMove() {
        /* Validate the castling move before perform the move. */
        if (match.validateCastlingPieces(source, target) && match.validateCastlingMove(source, target)) {
            executeCastlingMove(source, target);
            return;
        }

        /* If the movement isn't an especial move, perform a normal move. */
        executePieceMove();
    }

    /* Changes the position of a piece and make the capture of an opponent piece. */
    protected void executePieceMove() {
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

    /* Perform the castling especial move. */
    private void executeCastlingMove(Position kingPosition, Position rookPosition) {
        King king = (King) match.getBoard().getPieceOn(kingPosition);
        Rook rook = (Rook) match.getBoard().getPieceOn(rookPosition);

        /* Remove the king and rook from the board. */
        match.getBoard().removePiece(kingPosition);
        match.getBoard().removePiece(rookPosition);

        /* When the king's position is higher than the rook's position (further away), perform the long castling,
        otherwise perform the short castling. */
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

    /* Returns true if the player has any valid move to perform. */
    public boolean playerHasAnyValidMove() {
        for (int row = 0; row < match.getBoard().getRows(); row++) {
            for (int col = 0; col < match.getBoard().getRows(); col++) {
                ChessPiece piece = (ChessPiece) match.getBoard().getPieceOn(new Position(row, col));

                if (piece.hasAnyValidMove())
                    return true;
            }
        }
        return false;
    }
}