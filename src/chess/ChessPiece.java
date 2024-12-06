package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
    private final ChessColor color;
    private int moveCounter;

    public ChessPiece(Board board, ChessColor color) {
        super(board);
        this.color = color;
    }

    public ChessColor getColor() {
        return color;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

    public void addMoveCount() {
        this.moveCounter = moveCounter + 1;
    }

    public boolean hasMoved() {
        return getMoveCounter() > 0;
    }

    /**
     * Check if there is any piece at the position and the color of the piece.
     */
    public boolean validatePieceCapture(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().getPiece(position);
        return piece != null && piece.getColor() != getColor();
    }
}
