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

    @Override
    public boolean[][] possibleMoves(boolean captureMatters) {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position currentKingPosition = getPosition();

        // Check and return a boolean value the positions for each element in the matrix "directions"
        for (int[] direction : directions) {
            Position kingPosition = new Position(currentKingPosition.getRow() + direction[0],
                    currentKingPosition.getColumn() + direction[1]);

            if (getBoard().positionExists(kingPosition) && !getBoard().isThereAPieceAt(kingPosition)
                    || checkCapture(kingPosition)) {
                possibilities[kingPosition.getRow()][kingPosition.getColumn()] = true;
            }
        }
        return possibilities;
    }

    // Validate every king move and only permit the safe houses
    public boolean[][] possibleMoves() {
        /* The source matrix receive all possible moves for the king on the board. */
        boolean[][] source = possibleMoves(true);

        /* Result instance, which is the matrix to be returned by the method. */
        boolean[][] result = new boolean[8][8];

        /* Iterate through every position on the board. */
        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {
                Position position = new Position(a, b);
                Piece piece = match.getBoard().getPieceOn(position);
                boolean[][] aux;

                /* Do the merge of the source and auxiliary matrices if the position has an enemy piece. */
                if (match.validateCheckPosition(position)) {

                    /* The auxiliary matrix receive the piece possible movements. If the piece is a pawn,
                    only consider the capture positions (diagonal). */
                    if (piece instanceof Pawn) aux = piece.possibleMoves(false);
                    else aux = piece.possibleMoves(true);

                    /* Result receive the merge of the two matrices. */
                    result = mergePossibilities(aux, source);
                }
            }
        }
        return result;
    }

    /* Merge two matrices values. This method is used allow the king to move to safe squares, by setting
    false to all positions an enemy piece can move to. */
    private boolean[][] mergePossibilities(boolean[][] source, boolean[][] result) {
        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {
                /* If a position at the source is true, the result is marked as false at the same position in result. */
                if (source[a][b]) result[a][b] = false;
            }
        }
        return result;
    }
}
