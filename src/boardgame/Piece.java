package boardgame;

public abstract class Piece {
    private Position position;
    private Board board;

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

    public void setBoard(Board board) {
        this.board = board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    // Check all the position in the board
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
