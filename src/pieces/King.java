package pieces;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.ChessPiece;
import util.Util;

public class King extends ChessPiece {

    private final ChessMatch match;

    public King(Board board, ChessColor chessColor, ChessMatch match) {
        super(board, chessColor);
        this.match = match;
    }

    private final int[][] directions = {
            {0, -1}, // Up
            {0, 1},  // Down
            {-1, 0}, // Left
            {1, 0},  // Right
            {-1, -1}, // Up-Left
            {1, -1},  // Up-Right
            {-1, 1},  // Down-Left
            {1, 1}    // Down-Right
    };

    public int[][] getDirections() {
        return directions;
    }

    @Override
    public boolean[][] possibleMoves(boolean captureAllowed) {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position currentKingPosition = getPosition();

        /* The rook position's on the board. */
        final Position[] rookPositions = {
                new Position(this.getPosition().getRow() + 4,
                        this.getPosition().getColumn()
                ),
                new Position(this.getPosition().getRow() - 3,
                        this.getPosition().getColumn()
                )
        };

        if (captureAllowed) {

            /* Checks if it can move to any position in the matrix "directions". */
            for (int[] direction : directions) {
                Position kingPosition = new Position(
                        currentKingPosition.getRow() + direction[0],
                        currentKingPosition.getColumn() + direction[1]
                );

                if (getBoard().positionExists(kingPosition)
                        && getBoard().isPositionEmpty(kingPosition)
                        || validatePieceCapture(kingPosition)) {
                    possibilities[kingPosition.getRow()][kingPosition.getColumn()] = true;
                }
            }

            /* Marks as true the castling move position. */
            for (Position position : rookPositions) {
                if (match.validateCastlingMove(currentKingPosition, position))
                    possibilities[position.getRow()][position.getColumn()] = true;
            }

        } else {

            /* Marks all king directions as true on the board. */
            for (int[] direction : directions) {
                Position kingPosition = new Position(
                        currentKingPosition.getRow() + direction[0],
                        currentKingPosition.getColumn() + direction[1]
                );

                if (getBoard().positionExists(kingPosition))
                    possibilities[kingPosition.getRow()][kingPosition.getColumn()] = true;
            }
        }
        return possibilities;
    }

    /**
     * The source matrix receives all possible moves for the king on the board.
     * <p>
     * Possible moves will always receive true as parameter, because <code>source</code>
     * needs to have the possible moves of the piece considering the movements
     * and the captures.
     *
     * <p> Possible moves will only receive <code>false</code> as parameter when it needs
     * the possible movements of a piece until it encounters another piece on the board,
     * regardless of the piece's color.
     *
     * <p> The pawn can only capture on the diagonal, so you need to exclude the front
     * side movement, and for the king because the king cannot be in check,
     * it will not always be able to move to any square.
     * This will prevent some cases such as the king capturing a piece adjacent to
     * the opponent's king, leaving the king in check.
     */
    public boolean[][] possibleMoves() {
        boolean[][] source = possibleMoves(true);

        /* The result is the matrix will be returned by the method. */
        boolean[][] result = new boolean[8][8];

        /* Iterate through every position on the board. */
        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {
                Position position = new Position(a, b);
                Piece piece = match.getBoard().getPiece(position);
                boolean[][] aux;

                /* Do the merge of the source and auxiliary matrices if
                the position has an opponent piece. */
                if (match.validateOpponentPiecePosition(position)) {

                    /* The auxiliary matrix receives the piece possible movements.
                     * If the piece is a pawn, only consider the capture
                     * positions (diagonal).
                     * Otherwise, if the piece is a king, calculate all possible moves
                     * ignoring the king rules.
                     * For other pieces, ignore the squares filled with allied pieces.
                     */
                    aux = piece.possibleMoves(false);

                    /* Result receives the merge of the two matrices. */
                    result = Util.mergePossibilities(aux, source, false);
                }
            }
        }
        return result;
    }
}
