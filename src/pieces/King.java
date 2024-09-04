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

    // Check if there is a piece on the position and if it has, checks the color of the piece
    private boolean checkPossibleMoves(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return piece != null && piece.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] movePossibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];

        Position piece = new Position(0,0);

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
            piece.setPosition(getPosition().getColumn(), getPosition().getRow() - 1);
            if (getBoard().positionExists(getPosition()) && checkPossibleMoves(getPosition())) {
                movePossibilities[piece.getColumn()][piece.getRow()] = true;
            }
        }
        return movePossibilities;
    }
}
