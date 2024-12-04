package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessPiece;

public class Queen extends ChessPiece {

    public Queen(Board board, ChessColor chessColor) {
        super(board, chessColor);
    }

    private final int[][] directions = {
            {0, -1}, // Up
            {0, 1}, // Down
            {-1, 0}, // Left
            {1, 0}, // Right
            {-1, -1}, // Up-Left
            {1, -1}, // Down-Right
            {-1, 1}, // Down-Left
            {1, 1}, // Up-Right
    };

    @Override
    public boolean[][] possibleMoves(boolean captureAllowed) {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];
        Position queenPosition = getPosition();

        /* Iterate through all directions. */
        for (int[] direction : directions) {
            if (captureAllowed)
                checkQueenDirection(queenPosition, possibilities, direction);
            else
                checkDirectionWithoutCapture(queenPosition, possibilities, direction);
        }
        return possibilities;
    }

    private void checkQueenDirection(Position queen, boolean[][] possibilities, int[] arr) {
        Position position = new Position(queen.getRow() + arr[0], queen.getColumn() + arr[1]);

        while (getBoard().positionExists(position) && !getBoard().isPositionOccupied(position) || validatePieceCapture(position)) {
            /* If the piece can be captured, breaks the loop. */
            possibilities[position.getRow()][position.getColumn()] = true;

            /* Increment the value of the row and column until reach a piece or to the end of the board. */
            if (validatePieceCapture(position))
                break;

            position.setPosition(position.getRow() + arr[0], position.getColumn() + arr[1]);
        }
    }

    private void checkDirectionWithoutCapture(Position queen, boolean[][] possibilities, int[] arr) {
        Position position = new Position(queen.getRow() + arr[0], queen.getColumn() + arr[1]);

        while (getBoard().positionExists(position)) {
            /* If the piece can be captured, breaks the loop. */
            possibilities[position.getRow()][position.getColumn()] = true;

            /* If there is a piece at the position, breaks the loop. */
            if (getBoard().isPositionOccupied(position))
                break;

            position.setPosition(position.getRow() + arr[0], position.getColumn() + arr[1]);
        }
    }
}
