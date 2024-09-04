package chess;

import boardgame.Position;

public class ChessPosition {
    private Integer row;
    private char column;

    public ChessPosition(char column, Integer row) {
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instantiating 'ChessPosition'. Valid value are from A1 to H8.");
        }
        this.row = row;
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    protected Position toPosition() {
        return new Position(8 - row, column - 'a');
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
