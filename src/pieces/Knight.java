package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessPiece;

public class Knight extends ChessPiece{

    public Knight(Board board, ChessColor chessColor) {
        super(board, chessColor);
    }

    private final int[][] directions = {
            {-1, -2}, // Up-Left
            {1, -2}, // Up-Right
            {2, -1}, // Up-Right
            {2, 1}, // Down-Right
            {1, 2}, // Down-Right
            {-1, 2}, // Down-Left
            {-2, 1}, // Down-Left
            {-2, -1}, // Up-Left
    };

    @Override
    public boolean[][] possibleMoves(boolean captureAllowed) {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position currentKnightPosition = getPosition();

        for (int[] direction : directions) {
            Position newKnightPosition = new Position(
                    currentKnightPosition.getRow() + direction[0],
                    currentKnightPosition.getColumn() + direction[1]
            );

            if (captureAllowed) {
                knightPossibleMoves(newKnightPosition, possibilities);
            } else {
                possibleMovesWithoutCapture(newKnightPosition, possibilities);
            }
        }
        return possibilities;
    }

    private void knightPossibleMoves(Position position, boolean[][] possibilities) {
        if (getBoard().positionExists(position) && !getBoard().isThereAPieceAt(position)
                || validatePieceCapture(position)) {
            possibilities[position.getRow()][position.getColumn()] = true;
        }
    }

    private void possibleMovesWithoutCapture(Position position, boolean[][] possibilities) {
        if (getBoard().positionExists(position))
            possibilities[position.getRow()][position.getColumn()] = true;
    }
}
