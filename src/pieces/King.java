package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public abstract class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];

        Position king = new Position(0,0);

        int[][] directions = {
                {0, -1}, // Up
                {0, 1},  // Down
                {-1, 0}, // Left
                {1, 0},  // Right
                {-1, -1}, // Up-Left
                {1, -1},  // Up-Right
                {-1, 1},  // Down-Left
                {1, 1}    // Down-Right
        };

        // Check and return a boolean value the positions for each element in the matrix "directions"
        for (int[] direction : directions) {
            king.setPosition(getPosition().getColumn(), getPosition().getRow() - 1);
            if (getBoard().positionExists(getPosition()) && checkPossibleMoves(getPosition())) {
                possibilities[king.getColumn()][king.getRow()] = true;
            }
        }
        return possibilities;
    }
}
