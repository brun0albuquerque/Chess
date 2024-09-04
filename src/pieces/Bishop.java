package pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public abstract class Bishop extends ChessPiece {

    private final String bishop = "B";

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return bishop;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];
        return possibilities;
    }
}
