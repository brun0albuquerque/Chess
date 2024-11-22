package controller;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

import javax.swing.*;

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

    protected void handlePieceSelection(int x, int y) {
        Position position = new Position(x, y);
        ChessPiece selectedPiece = (ChessPiece) match.getBoard().getPieceOnBoard(position);

        System.out.println("Coord: " + x + ", " + y);

        if (isAllCoordinatesNull() && selectedPiece != null && match.validatePieceColor(position)) {
            aX = x;
            aY = y;
        } else if (aX != null && aY != null) {
            bX = x;
            bY = y;

            if (aX.equals(bX) && aY.equals(bY)) {
                cleanAllCoordinates();
            }
        }
    }

    protected void logicMovement(ChessMatch match) {
        if (match.getPieces()[aX][aY] == null) {
            JOptionPane.showMessageDialog(null, "There is no piece to move.",
                    "Move error", JOptionPane.INFORMATION_MESSAGE, null);
            return;
        }

        Position source = new Position(aX, aY);
        Position target = new Position(bX, bY);

        if (match.validateSourcePosition(source) && match.validateTargetPosition(target)) {
            movements.chessPieceMovement(source, target);
        }
    }

    protected boolean isAllCoordinatesNull() {
        return aX == null || aY == null || bX == null || bY == null;
    }

    protected void cleanAllCoordinates() {
        aX = null;
        aY = null;
        bX = null;
        bY = null;
    }
}
