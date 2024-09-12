package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.ChessColor;

public class Bishop extends ChessPiece {

    public Bishop(Board board, ChessColor chessColor) {
        super(board, chessColor);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position currentBishopPosition = getPosition();

        // All the diagonal directions
        checkBishopDirection(currentBishopPosition, possibilities, -1, -1); // Up-Left
        checkBishopDirection(currentBishopPosition, possibilities, 1, 1); // Down-Right
        checkBishopDirection(currentBishopPosition, possibilities, 1, -1); // Down-Left
        checkBishopDirection(currentBishopPosition, possibilities, -1, 1); // Up-Right

        return possibilities;
    }

    private void checkBishopDirection(Position bishop, boolean[][] matrix, int x, int y) {
        Position position = new Position(bishop.getRow() + x, bishop.getColumn() + y);
        while (!getBoard().thereIsAPiece(position) || checkPossibleCapture(position)) {
            matrix[position.getRow()][position.getColumn()] = true;

            if (checkPossibleCapture(position)) break; // If the piece can be captured, break the loop

            // Increment the value of the row and column until reach a piece or to the end of the board
            position.setPosition(position.getRow() + x, position.getColumn() + y);
        }
    }
}
