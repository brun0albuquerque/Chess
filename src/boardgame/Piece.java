package boardgame;

public abstract class Piece {
    private Position position;
    private final Board board;

    public Piece(Board board) {
        this.position = null;
        this.board = board;
    }

    public Piece(Position position, Board board) {
        this.position = null;
        this.board = board;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possiblePieceMoves(Position position) {
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
