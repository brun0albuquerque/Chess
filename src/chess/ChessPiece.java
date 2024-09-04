package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {
    private final Color color;
    private Integer moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Integer getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(Integer moveCount) {
        this.moveCount = moveCount;
    }
}
