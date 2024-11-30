package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
    private final ChessColor color;
    private int moveCount;

    public ChessPiece(Board board, ChessColor color) {
        super(board);
        this.color = color;
    }

    public ChessColor getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void addMoveCount() {
        this.moveCount = moveCount + 1;
    }

    public boolean pieceMoved() {
        return getMoveCount() != 0;
    }

    /* Check if there is any piece at the position and the color of the piece. */
    public boolean checkCapture(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().getPieceOn(position);
        return piece != null && piece.getColor() != getColor();
    }
}
