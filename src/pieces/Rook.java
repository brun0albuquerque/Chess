package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public abstract class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];

        Position rook = new Position(0, 0);

        rook.setPosition(getPosition().getColumn(), getPosition().getRow());
        if (getBoard().positionExists(getPosition()) && checkPossibleMoves(getPosition())) {
            possibilities[rook.getColumn()][rook.getRow()] = true;
        }
        return possibilities;
    }
}
