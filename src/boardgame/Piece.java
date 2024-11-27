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

    public abstract boolean[][] possibleMoves();

    //Check if there is any move for the piece to make on the board
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

    public boolean isThereAnyPossibleMove(boolean[][] matrix) {
        for (boolean[] rows : matrix) {
            for (boolean column : rows) {
                if (column) return true;
            }
        }
        return false;
    }
}
