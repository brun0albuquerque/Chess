package controller;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

public class MouseActions {

    private final Movements movements;
    private final ChessMatch match;

    protected static Integer aX = null;
    protected static Integer aY = null;
    protected static Integer bX = null;
    protected static Integer bY = null;

    public MouseActions(Movements movements, ChessMatch match) {
        this.movements = movements;
        this.match = match;
    }

    public Movements getMovements() {
        return movements;
    }

    /* Handles the player interactions on the board. */
    protected void handlePieceSelection(int x, int y) {
        Position position = new Position(x, y);
        ChessPiece selectedPiece = (ChessPiece) match.getBoard().getPieceOn(position);

        if (isAllCoordinatesNull() && selectedPiece != null && match.validatePieceColor(position)) {

            if (aX != null && aY != null && match.validateCastlingPieces(new Position(MouseActions.aX, MouseActions.aY), position)) {
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

    /* Perform the logic move on the board. */
    protected void logicMove(ChessMatch match) {
        if (match.getPieces()[aX][aY] == null)
            return;

        Position source = new Position(aX, aY);
        Position target = new Position(bX, bY);

        if (match.validateSourcePosition(source) && match.validateTargetPosition(target)) {

            /* Validate if the move can be done checking the target position on the board.
             * If the piece is the king, it only allows the move to safe squares. */
            if (!movements.validateMovePosition(source, target))
                cleanAllCoordinates();

                /* If the movement isn't an especial move, make a simple move. */
            else
                movements.pieceMove(source, target);
        } else if (match.validateCastlingPieces(source, target)) {

            /* Validate the castling move before make the move. */
            if (match.validateCastlingMove(source, target))
                movements.castlingMove(source, target);
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
}