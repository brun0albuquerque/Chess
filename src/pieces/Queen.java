package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.ChessColor;

public class Queen extends ChessPiece {

    public Queen(Board board, ChessColor chessColor) {
        super(board, chessColor);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];

        Position currentQueenPosition = getPosition();

        // All the sides directions
        checkQueenDirection(currentQueenPosition, possibilities, 0, -1); // Up
        checkQueenDirection(currentQueenPosition, possibilities, 0, 1); // Down
        checkQueenDirection(currentQueenPosition, possibilities, -1, 0); // Left
        checkQueenDirection(currentQueenPosition, possibilities, 1, 0); // Right

        // All the diagonal directions
        checkQueenDirection(currentQueenPosition, possibilities, -1, -1); // Up-Left
        checkQueenDirection(currentQueenPosition, possibilities, 1, -1); // Up-Right
        checkQueenDirection(currentQueenPosition, possibilities, -1, 1); // Down-Left
        checkQueenDirection(currentQueenPosition, possibilities, 1, 1); // Down-Right

        return possibilities;
    }

    private void checkQueenDirection(Position queen, boolean[][] matrix, int x, int y) {
        Position position = new Position(queen.getRow() + x, queen.getColumn() + y);

        while (getBoard().positionExists(position) && !getBoard().isThereAPieceAt(position)
                || checkPossibleCapture(position)) {

            // If the piece can be captured, breaks the loop
            matrix[position.getRow()][position.getColumn()] = true;

            // Increment the value of the row and column until reach a piece or to the end of the board
            if (checkPossibleCapture(position)) break;

            position.setPosition(position.getRow() + x, position.getColumn() + y);
        }
    }
}
