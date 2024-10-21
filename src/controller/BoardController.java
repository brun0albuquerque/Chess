package controller;

import boardgame.Position;
import chess.ChessMatch;

import javax.swing.*;

public class BoardController {

    private ChessMatch match;

    private static Integer aX = null;
    private static Integer aY = null;
    private static Integer bX = null;
    private static Integer bY = null;

    public BoardController(ChessMatch match) {
        this.match = match;
    }

    public Integer getaX() {
        return aX;
    }

    public Integer getaY() {
        return aY;
    }

    public Integer getbX() {
        return bX;
    }

    public Integer getbY() {
        return bY;
    }

    public void handlePieceSelection(int x, int y) {

        System.out.println("PIECE: " + match.getBoard().pieceOnBoard(new Position(x, y)));

        if (aX == null && aY == null) {
            aX = x;
            aY = y;
        } else if (bX == null && bY == null) {
            bX = x;
            bY = y;
        }
    }

    public void movePiecesLogically(ChessMatch match) {
//        if (match.getPieces()[aX][aY] == null) {
//            JOptionPane.showMessageDialog(null, "No piece to move.",
//                    "Move error", JOptionPane.INFORMATION_MESSAGE, null);
//            return;
//        }
        Position source = new Position(aX, aY);
        Position target = new Position(bX, bY);
        match.performChessMove(source, target);
    }

    public static void cleanPointers() {
        aX = null;
        aY = null;
        bX = null;
        bY = null;
    }
}
