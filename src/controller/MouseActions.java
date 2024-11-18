package controller;

import boardgame.Position;
import chess.ChessMatch;

import javax.swing.*;

public class MouseActions {

    private final Moviments moviments;
    private final ChessMatch match;

    private Integer aX = null;
    private Integer aY = null;
    private Integer bX = null;
    private Integer bY = null;

    public MouseActions(Moviments moviments, ChessMatch match) {
        this.moviments = moviments;
        this.match = match;
    }

    public Integer getaX() {
        return aX;
    }

    public void setaX(Integer aX) {
        this.aX = aX;
    }

    public Integer getaY() {
        return aY;
    }

    public void setaY(Integer aY) {
        this.aY = aY;
    }

    public Integer getbX() {
        return bX;
    }

    public void setbX(Integer bX) {
        this.bX = bX;
    }

    public Integer getbY() {
        return bY;
    }

    public void setbY(Integer bY) {
        this.bY = bY;
    }

    public void handlePieceSelection(int x, int y) {

        System.out.println("PIECE: " + match.getBoard().getPieceOnBoard(new Position(x, y)));

        if (aX == null && aY == null && match.validatePieceColor(new Position(x, y))) {
            aX = x;
            aY = y;
        } else if (bX == null && bY == null) {
            bX = x;
            bY = y;
        }
/*        ChessPiece piece;
        if (aX != null && aY != null) {
            piece = (ChessPiece) match.getBoard().getBoardPieces()[aX][aY];
        }*/

        try {
            if (aX != null && aY != null && aX.equals(bX) && aY.equals(bY)) cleanPointers();
            if (bX != null && bY != null) match.validateTargetPosition(new Position(bX, bY));
        } catch (RuntimeException e) {
            cleanPointers();
        }
        match.nextTurn();
    }

    public void logicMovement(ChessMatch match) {
        if (match.getPieces()[aX][aY] == null) {
            JOptionPane.showMessageDialog(null, "There is no piece to move.",
                    "Move error", JOptionPane.INFORMATION_MESSAGE, null);
            return;
        }
        Position source = new Position(aX, aY);
        Position target = new Position(bX, bY);
        moviments.chessPieceMovement(source, target);
    }

    public void cleanPointers() {
        setaX(null);
        setaY(null);
        setbX(null);
        setbY(null);
    }
}
