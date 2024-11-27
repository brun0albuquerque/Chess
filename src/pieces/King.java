package pieces;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.ChessPiece;

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
            Position kingPosition = new Position(currentKingPosition.getRow() + direction[0],
                    currentKingPosition.getColumn() + direction[1]);

            if (getBoard().positionExists(kingPosition) && !getBoard().isThereAPieceAt(kingPosition)
                    || checkPossibleCapture(kingPosition)) {
                possibilities[kingPosition.getRow()][kingPosition.getColumn()] = true;
            }
        }
        return possibilities;
    }

    // Validate every king move to only permit the safe houses
    public boolean[][] possibleMoves(boolean[][] source) {
        boolean[][] result = new boolean[8][8];

        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {
                Position position = new Position(a, b);
                Piece piece = match.getBoard().getPieceOn(position);
                boolean[][] aux;

                if (match.validateCheckPosition(position)) {
                    if (piece instanceof Pawn) {
                        aux = ((Pawn) piece).offensiveMoves();
                    } else {
                        aux = piece.possibleMoves();
                    }
                    result = mergePossibilities(aux, source);

                }
            }
        }
        return result;
    }

    // Merges the enemy's moves matrices with the king's moves matrices
    private boolean[][] mergePossibilities(boolean[][] source, boolean[][] result) {
        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {
                if (source[a][b]) result[a][b] = false;
            }
        }
        return result;
    }
}
