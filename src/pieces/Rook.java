package pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public abstract class Rook extends ChessPiece {

    private final String rook = "R";

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return rook;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];
        return possibilities;
    }
}
