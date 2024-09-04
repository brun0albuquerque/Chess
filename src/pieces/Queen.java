package pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public abstract class Queen extends ChessPiece {

    private final String queen = "Q";

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return queen;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];
        return possibilities;
    }
}
