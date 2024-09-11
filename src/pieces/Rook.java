package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public abstract class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];

        Position currentRookPosition = getPosition();

        // All the sides directions
        checkRookDirection(currentRookPosition, possibilities, -1, 0); // Up
        checkRookDirection(currentRookPosition, possibilities, 1, 0); // Down
        checkRookDirection(currentRookPosition, possibilities, 0, -1); // Left
        checkRookDirection(currentRookPosition, possibilities, 0, 1); // Right

        return possibilities;
    }

    private void checkRookDirection(Position rook, boolean[][] matrix, int x, int y) {
        Position position = new Position(rook.getRow() + x, rook.getColumn() + y);
        while (!getBoard().thereIsAPiece(position) || checkPossibleCapture(position)) {

            matrix[position.getRow()][position.getColumn()] = true;// If the piece can be captured, break the loop

            // Increment the value of the row and column until reach a piece or to the end of the board
            if (checkPossibleCapture(position)) break;
            position.setPosition(position.getRow() + x, position.getColumn() + y);
        }
    }
}
