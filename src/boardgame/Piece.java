package boardgame;

public abstract class Piece {
    private final Board board;
    private Position position;

    public Piece(Board board) {
        this.board = board;
        this.position = null;
    }

    public Piece(Board board, Position position) {
        this.board = board;
        this.position = position;
    }

    public Board getBoard() {
        return board;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /* This method must return all possibilities of movements for a piece on the board. There are two uses for this
    method: all movements checking the empty squares and the capture of an enemy piece or the empty squares and all
    pieces positions on the board, doesn't matter weather is its color.  The second one will be used to check if
    the king is in check. */
    public abstract boolean[][] possibleMoves(boolean captureMatters);

    /* Check if there is any move for the piece to make on the board. */
    public boolean isThereAnyPossibleMove() {
        boolean[][] matrix = possibleMoves(true);
        for (boolean[] rows : matrix) {
            for (boolean column : rows) {
                if (column) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereAnyPossibleMove(boolean[][] matrix) {
        for (boolean[] rows : matrix) {
            for (boolean column : rows) {
                if (column) return true;
            }
        }
        return false;
    }
}
