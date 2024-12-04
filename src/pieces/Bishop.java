package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessPiece;

public class Bishop extends ChessPiece {

    public Bishop(Board board, ChessColor chessColor) {
        super(board, chessColor);
    }

    private final int[][] directions = {
            {-1, -1}, // Up-Left
            {1, 1}, // Down-Right
            {-1, 1}, // Down-Left
            {1, -1}, // Up-Right
    };

    @Override
    public boolean[][] possibleMoves(boolean captureAllowed) {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position currentBishopPosition = getPosition();

        for (int[] direction : directions) {
            if (captureAllowed)
                bishopPossibleMoves(currentBishopPosition, possibilities, direction);
            else
                possibleMovesWithoutCapture(currentBishopPosition, possibilities, direction);
        }
        return possibilities;
    }

    private void bishopPossibleMoves(Position bishop, boolean[][] possibilities, int[] arr) {
        Position position = new Position(bishop.getRow() + arr[0], bishop.getColumn() + arr[1]);

        while (getBoard().positionExists(position) && getBoard().isPositionEmpty(position) || validatePieceCapture(position)) {
            possibilities[position.getRow()][position.getColumn()] = true;

            // If the piece can be captured, break the loop
            if (validatePieceCapture(position))
                break;

            // Increment the value of the row and column until reach a piece or to the end of the board
            position.setPosition(position.getRow() + arr[0], position.getColumn() + arr[1]);
        }
    }

    private void possibleMovesWithoutCapture(Position bishop, boolean[][] possibilities, int[] arr) {
        Position position = new Position(bishop.getRow() + arr[0], bishop.getColumn() + arr[1]);

        while (getBoard().positionExists(position)) {
            possibilities[position.getRow()][position.getColumn()] = true;

            // If there is a piece at the position, breaks the loop
            if (!getBoard().isPositionEmpty(position))
                break;

            // Increment the value of the row and column until reach a piece or to the end of the board
            position.setPosition(position.getRow() + arr[0], position.getColumn() + arr[1]);
        }
    }
}
