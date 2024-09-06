package chess;

public class ChessPosition {
    private final Integer row;
    private final Integer column;

    public ChessPosition(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "ChessPosition{" + "row=" + row + ", column=" + column + '}';
    }
}
