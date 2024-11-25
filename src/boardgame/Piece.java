package boardgame;

public abstract class Piece {
    private final Board board;
    private Position position;

    public Piece(Board board) {
        this.board = board;
        this.position = null;
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

    public abstract boolean[][] possibleMoves();

    public boolean possibleMoveFor(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    // Check if there is any movement that the piece can move on the board
    public boolean isThereAnyPossibleMove() {
        boolean[][] matrix = possibleMoves();
        for (boolean[] rows : matrix) {
            for (boolean column : rows) {
                if (column) {
                    return true;
                }
            }
        }
        return false;
    }
}
