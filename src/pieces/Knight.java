package pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public abstract class Knight extends ChessPiece {

    private final String knight = "L";

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return knight;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];
        return possibilities;
    }
}
