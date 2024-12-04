package controller;

import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.KingNotFoundException;
import pieces.King;
import pieces.Rook;
import util.Util;

public class GameController {

    private final ChessMatch match;

    protected static Integer aX = null;
    protected static Integer aY = null;
    protected static Integer bX = null;
    protected static Integer bY = null;

    protected static Position source = null;
    protected static Position target = null;

    public static boolean playerHasLegalMoves;

    public GameController(ChessMatch match) {
        this.match = match;
    }

    /**
     * Handles the player interactions on the board.
     * @param x the row coordinate of the player interaction on the screen.
     * @param y the column coordinate of the player interaction on the screen.
     */
    protected void handleScreenSelection(int x, int y) {
        Position position = new Position(x, y);
        ChessPiece selectedPiece = (ChessPiece) match.getBoard().getPiece(position);

        if (isAllCoordinatesNull() && Util.isObjectNotNull(selectedPiece) && match.validatePieceColor(position)) {

            if (aX != null && aY != null && match.validateCastlingPieces(
                    new Position(GameController.aX, GameController.aY), position)) {
                bX = x;
                bY = y;
                target = new Position(GameController.bX, GameController.bY);
                return;
            }
            aX = x;
            aY = y;
            source = new Position(GameController.aX, GameController.aY);

        } else if (aX != null && aY != null) {
            bX = x;
            bY = y;
            target = new Position(GameController.bX, GameController.bY);
        }
    }

    /**
     * Checks if any coordinate is not null.
     * @return true if all coordinates are null.
     */
    protected boolean isAllCoordinatesNull() {
        return aX == null || aY == null || bX == null || bY == null || source == null || target == null;
    }

    /**
     * Sets all coordinates null.
     */
    protected void cleanAllCoordinates() {
        source = null;
        target = null;
        aX = null;
        aY = null;
        bX = null;
        bY = null;
    }

    /**
     * This method validates if a move can be performed in the game.
     */
    protected boolean validateLogicMove() throws KingNotFoundException {
        playerHasLegalMoves = playerHasAnyLegalMove();
        match.isKingInCheck(source, target);
        match.checkmate = match.isCheckmate();
        match.stalemate = match.isStalemate();

        if (match.kingCheck) {
            cleanAllCoordinates();
            return false;
        }

        if (!match.validateSourcePosition(source) && !match.validateTargetPosition(target))
            return false;

        /* Validate if the move can be done checking the target position on the board.
         * If the piece is the king, it only allows the move to safe squares. */
        if (!match.validateMoveExecution(source, target)) {
            cleanAllCoordinates();
            return false;
        }
        return true;
    }

    /**
     * Perform one of the three possible moves: a normal move, a castling move or en passant.
     */
    protected void performChessMove() {
        /* Validate the castling move before perform the move. */
        if (match.validateCastlingPieces(source, target) && match.validateCastlingMove(source, target)) {
            performCastlingMove(source, target);
            return;
        }

        /* If the movement isn't an especial move, perform a normal move. */
        match.performPieceMove(source, target);
    }

    /**
     * Perform the castling move.
     */
    private void performCastlingMove(Position kingPosition, Position rookPosition) {
        King king = (King) match.getBoard().getPiece(kingPosition);
        Rook rook = (Rook) match.getBoard().getPiece(rookPosition);

        match.getBoard().removePiece(kingPosition);
        match.getBoard().removePiece(rookPosition);

        /* When the king's position is higher than the rook's position (further away), perform the long castling,
        otherwise perform the short castling. */
        int kingRow = (kingPosition.getRow() > rookPosition.getRow()) ? kingPosition.getRow() - 2 : kingPosition.getRow() + 2;
        int rookRow = (kingPosition.getRow() > rookPosition.getRow()) ? rookPosition.getRow() + 2 : rookPosition.getRow() - 3;
        Position kingAfterCastling = new Position(kingRow, kingPosition.getColumn());
        Position rookAfterCastling = new Position(rookRow, rookPosition.getColumn());

        match.getBoard().placePiece(kingAfterCastling, king);
        match.getBoard().placePiece(rookAfterCastling, rook);
        match.nextTurn();
    }

    /**
     * Check if the player has any legal move to do in the game.
     * @return true if the player has any valid move to perform, else false.
     */
    public boolean playerHasAnyLegalMove() {
        Piece[][] boardPieces = match.getBoard().getBoardPieces();

        for (Piece[] boardRow : boardPieces) {
            for (Piece piece : boardRow) {

                if (Util.isObjectNotNull(piece) && ((ChessPiece) piece).getColor().equals(match.getPlayerColor())) {
                    if (piece.hasAnyLegalMove())
                        return true;
                }
            }
        }
        return false;
    }
}