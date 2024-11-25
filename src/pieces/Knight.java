package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessPiece;

public class Knight extends ChessPiece {

    public Knight(Board board, ChessColor chessColor) {
        super(board, chessColor);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position currentKnightPosition = getPosition();

        int[][] directions = {
                {-1, -2}, // Up-Left
                {1, -2}, // Up-Right
                {2, -1}, // Up-Right
                {2, 1}, // Down-Right
                {1, 2}, // Down-Right
                {-1, 2}, // Down-Left
                {-2, 1}, // Down-Left
                {-2, -1}, // Up-Left
        };

        for (int[] direction : directions) {
            Position newKnightPosition = new Position(
                    currentKnightPosition.getRow() + direction[0],
                    currentKnightPosition.getColumn() + direction[1]
            );

            if (getBoard().positionExists(newKnightPosition) && !getBoard().isThereAPieceAt(newKnightPosition)
                    || checkPossibleCapture(newKnightPosition)) {
                possibilities[newKnightPosition.getRow()][newKnightPosition.getColumn()] = true;
            }
        }
        return possibilities;
    }
}
