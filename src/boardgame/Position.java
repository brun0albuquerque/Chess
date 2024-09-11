package boardgame;

public class Position {
    private Integer row;
    private Integer column;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setPosition(int column, int row){
        this.column = column;
        this.row = row;
    }
}
