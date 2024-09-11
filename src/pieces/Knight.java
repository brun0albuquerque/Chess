package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public abstract class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position currentKnightPosition = getPosition();

        int[][] directions = {
                {-2, -1}, // Up-Left
                {-2, 1}, // Up-Right
                {2, -1}, // Down-Left
                {2, 1}, // Down-Right
                {-1, -2}, // Left-Up
                {-1, 2}, // Right-Up
                {1, -2}, // Left-Down
                {1, 2}, // Right-Down
        };

        for (int[] direction : directions) {
            Position knightPosition = new Position(currentKnightPosition.getRow() + direction[0], currentKnightPosition.getColumn() + direction[1]);
            if (!getBoard().thereIsAPiece(knightPosition) && checkPossibleCapture(knightPosition)) {
                possibilities[knightPosition.getRow()][knightPosition.getColumn()] = true;
            }
        }
        return possibilities;
    }


}
