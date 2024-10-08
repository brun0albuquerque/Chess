package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
    private final ChessColor chessColor;
    private int moveCount;

    public ChessPiece(Board board, ChessColor chessColor) {
        super(board);
        this.chessColor = chessColor;
    }

    public ChessColor getColor() {
        return chessColor;
    }

    public int getMoveCount() {
        return moveCount;
    }

    protected void increaseMoveCount() {
        moveCount++;
    }

    protected void decreaseMoveCount() {
        moveCount--;
    }

    // Check if there is a piece on the position and if it has, checks the color of the piece
    public boolean checkPossibleCapture(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().pieceOnBoard(position);
        return piece != null && piece.getColor() != getColor();
    }
}
