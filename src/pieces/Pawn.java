package pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public abstract class Pawn extends ChessPiece {

    private final String pawn = "P";

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return pawn;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];
        return possibilities;
    }
}
