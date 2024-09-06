package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public abstract class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
        setMoveCount(0);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getColumns()][getBoard().getRows()];

        Position piece = new Position(0,0);

        if (getMoveCount() == 0) {
            piece.setPosition(getPosition().getColumn(), getPosition().getRow() + 2);
            if (getBoard().positionExists(getPosition()) && checkPossibleMoves(getPosition())) {
                possibilities[piece.getColumn()][piece.getRow()] = true;
            }

        }

        piece.setPosition(getPosition().getColumn(), getPosition().getRow() + 1);
        if (getBoard().positionExists(getPosition()) && checkPossibleMoves(getPosition())) {
            possibilities[piece.getColumn()][piece.getRow()] = true;
        }

        return possibilities;
    }
}
