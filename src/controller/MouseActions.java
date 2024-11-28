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

    /* Handle the player interactions on the board. */
    protected void handlePieceSelection(int x, int y) {
        Position position = new Position(x, y);
        ChessPiece selectedPiece = (ChessPiece) match.getBoard().getPieceOn(position);

        if (isAllCoordinatesNull() && selectedPiece != null && match.validatePieceColor(position)) {
            aX = x;
            aY = y;
        } else if (aX != null && aY != null) {
            bX = x;
            bY = y;
        }
    }

    /* Perform the logic move on the board. */
    protected void logicMove(ChessMatch match) {
        if (match.getPieces()[aX][aY] == null) return;

        Position source = new Position(aX, aY);
        Position target = new Position(bX, bY);

        if (match.validateSourcePosition(source) && match.validateTargetPosition(target)) {
            if (!movements.pieceMove(source, target)) cleanAllCoordinates();
        }
    }

    /* Checks if any coordinate is null. */
    protected boolean isAllCoordinatesNull() {
        return aX == null || aY == null || bX == null || bY == null;
    }

    /* Set all coordinates null. */
    protected void cleanAllCoordinates() {
        aX = null;
        aY = null;
        bX = null;
        bY = null;
    }
}
