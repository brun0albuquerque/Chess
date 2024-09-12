package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessColor;

public class King extends ChessPiece {

    private final ChessMatch match;

    public King(Board board, ChessColor chessColor, ChessMatch match) {
        super(board, chessColor);
        this.match = match;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position currentKingPosition = getPosition();

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
            Position kingPosition = new Position(currentKingPosition.getRow() + direction[0], currentKingPosition.getColumn() + direction[1]);
            if (!getBoard().thereIsAPiece(kingPosition) && checkPossibleCapture(kingPosition)) {
                possibilities[kingPosition.getRow()][kingPosition.getColumn()] = true;
            }
        }
        return possibilities;
    }
}
