package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessPiece;

public class Rook extends ChessPiece {

    public Rook(Board board, ChessColor chessColor) {
        super(board, chessColor);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position currentRookPosition = getPosition();

        // All the sides directions
        checkRookDirection(currentRookPosition, possibilities, 0, -1); // Up
        checkRookDirection(currentRookPosition, possibilities, 0, 1); // Down
        checkRookDirection(currentRookPosition, possibilities, -1, 0); // Left
        checkRookDirection(currentRookPosition, possibilities, 1, 0); // Right

        return possibilities;
    }

    private void checkRookDirection(Position rook, boolean[][] matrix, int x, int y) {
        Position position = new Position(rook.getRow() + x, rook.getColumn() + y);

        while (getBoard().positionExists(position) && !getBoard().isThereAPieceAt(position)
                || checkPossibleCapture(position)) {

            matrix[position.getRow()][position.getColumn()] = true;

            // If the piece can be captured, breaks the loop
            if (checkPossibleCapture(position)) break;

            // Increment the value of the row and column until reach a piece or to the end of the board
            position.setPosition(position.getRow() + x, position.getColumn() + y);
        }
    }
}
