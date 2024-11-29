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

    /* Every king move. */
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
    public boolean[][] possibleMoves(boolean captureMatters) {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position currentKingPosition = getPosition();

        /* The rook position's on the board. */
        final Position[] rookPositions = {
                new Position(this.getPosition().getRow() + 4, this.getPosition().getColumn()),
                new Position(this.getPosition().getRow() - 3, this.getPosition().getColumn())
        };

        if (captureMatters) {

            /* Checks if it can move to any position in the matrix "directions". */
            for (int[] direction : directions) {
                Position kingPosition = new Position(currentKingPosition.getRow() + direction[0],
                        currentKingPosition.getColumn() + direction[1]);

                if (getBoard().positionExists(kingPosition) && !getBoard().isThereAPieceAt(kingPosition)
                        || checkCapture(kingPosition)) {
                    possibilities[kingPosition.getRow()][kingPosition.getColumn()] = true;
                }
            }

            /* Checks if it can make the castle move. */
            for (Position position : rookPositions) {
                if (match.validateCastleMove(currentKingPosition, position)) {
                    possibilities[position.getRow()][position.getColumn()] = true;
                }
            }

        } else {

            /* Marks all king directions as true on the board. */
            for (int[] direction : directions) {
                Position kingPosition = new Position(currentKingPosition.getRow() + direction[0],
                        currentKingPosition.getColumn() + direction[1]);

                if (getBoard().positionExists(kingPosition)) {
                    possibilities[kingPosition.getRow()][kingPosition.getColumn()] = true;
                }
            }
        }
        return possibilities;
    }

    /* Calculate all the possible moves for the king and the opponent pieces. This method differs from the Piece one
     * by its return. This method will check each position the king can move, leaving as true only the ones it can't be
     * in check. */
    public boolean[][] possibleMoves() {

        /*
         * The source matrix receives all possible moves for the king on the board.
         *
         * Possible moves will always receive true as parameter, because source needs to have the possible moves
         * of the piece considering the movements and the captures.
         *
         * Possible moves will only receive false as parameter when you need the possible movements of a piece
         * until it encounters another piece on the board, regardless of the piece's color. It will be used only with
         * the king and the pawn.
         *
         * The pawn can only capture on the diagonal, so you need to exclude the front side movement, and for
         * the king because the king cannot be in check, it will not always be able to move to any square.
         * This will prevent some cases such as the king capturing a piece adjacent to the opponent's king,
         * leaving the king in check.
         * */
        boolean[][] source = possibleMoves(true);

        /* The result is the matrix will be returned by the method. */
        boolean[][] result = new boolean[8][8];

        /* Iterate through every position on the board. */
        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {
                Position position = new Position(a, b);
                Piece piece = match.getBoard().getPieceOn(position);
                boolean[][] aux;

                /* Do the merge of the source and auxiliary matrices if the position has an opponent piece. */
                if (match.validateOpponentPiecePosition(position)) {

                    /* The auxiliary matrix receives the piece possible movements. If the piece is a pawn,
                     * only consider the capture positions (diagonal). Otherwise, if the piece is a king, calculate all
                     * possible moves ignoring the king rules. */
                    if (piece instanceof Pawn) aux = piece.possibleMoves(false);
                    else if (piece instanceof King) aux = piece.possibleMoves(false);
                    else aux = piece.possibleMoves(true);

                    /* Result receive the merge of the two matrices. */
                    result = mergePossibilities(aux, source, false);
                }
            }
        }
        return result;
    }

    /**
     * Merge two matrices values.
     * This method is used to allow the king to move to safe squares, by setting false to all
     * positions an opponent piece can move to.
     *
     * @param source is the matrix which will be analyzed if the given position in the loop is true.
     * @param result is a matrix which will receive false if the loop position if the same position in source is true.
     * @param value is the value result will receive.
     * @return result after iterating through a source.
     */
    public boolean[][] mergePossibilities(boolean[][] source, boolean[][] result, boolean value) {
        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {

                /* If a position at the source is true, the result is marked as false at the same position in result. */
                if (source[a][b]) result[a][b] = value;
            }
        }
        return result;
    }
}
