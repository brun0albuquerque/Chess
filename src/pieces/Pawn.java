package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.ChessPiece;

public class Pawn extends ChessPiece {

    public Pawn(Board board, ChessColor chessColor, ChessMatch match) {
        super(board, chessColor);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        if (getColor() == ChessColor.WHITE) {

            Position oneStepWhite = new Position(getPosition().getRow(), getPosition().getColumn() + 1);
            Position twoStepsWhite = new Position(getPosition().getRow(), getPosition().getColumn() + 2);
            Position leftDiagonalWhite = new Position(getPosition().getRow() - 1, getPosition().getColumn() + 1);
            Position rightDiagonalWhite = new Position(getPosition().getRow() + 1, getPosition().getColumn() + 1);

            // One house move
            if (getBoard().positionExists(oneStepWhite) && !getBoard().isThereAPieceAt(oneStepWhite)) {
                possibilities[oneStepWhite.getRow()][oneStepWhite.getColumn()] = true;
            }

            // Two houses move
            if (getBoard().positionExists(oneStepWhite) && !getBoard().isThereAPieceAt(oneStepWhite)
                    && getBoard().positionExists(twoStepsWhite) && !getBoard().isThereAPieceAt(twoStepsWhite)
                    && getMoveCount() == 0) {
                possibilities[twoStepsWhite.getRow()][twoStepsWhite.getColumn()] = true;
            }

            // Capturing a piece on the left diagonal
            if (getBoard().positionExists(leftDiagonalWhite) && getBoard().isThereAPieceAt(leftDiagonalWhite)
                    && checkPossibleCapture(leftDiagonalWhite)) {
                possibilities[leftDiagonalWhite.getRow()][leftDiagonalWhite.getColumn()] = true;
            }

            // Capturing a piece on the right diagonal
            if (getBoard().positionExists(rightDiagonalWhite) && getBoard().isThereAPieceAt(rightDiagonalWhite)
                    && checkPossibleCapture(rightDiagonalWhite)) {
                possibilities[rightDiagonalWhite.getRow()][rightDiagonalWhite.getColumn()] = true;
            }
        }

        if (getColor() == ChessColor.BLACK) {

            Position oneStepBlack = new Position(getPosition().getRow(), getPosition().getColumn() - 1);
            Position twoStepsBlack = new Position(getPosition().getRow(), getPosition().getColumn() - 2);
            Position leftDiagonalBlack = new Position(getPosition().getRow() + 1, getPosition().getColumn() - 1);
            Position rightDiagonalBlack = new Position(getPosition().getRow() - 1, getPosition().getColumn() - 1);

            // One house move
            if (getBoard().positionExists(oneStepBlack) && !getBoard().isThereAPieceAt(oneStepBlack)) {
                possibilities[oneStepBlack.getRow()][oneStepBlack.getColumn()] = true;
            }

            // Two houses move
            if (getBoard().positionExists(oneStepBlack) && !getBoard().isThereAPieceAt(oneStepBlack)
                    && getBoard().positionExists(twoStepsBlack) && !getBoard().isThereAPieceAt(twoStepsBlack)
                    && getMoveCount() == 0) {
                possibilities[twoStepsBlack.getRow()][twoStepsBlack.getColumn()] = true;
            }

            // Capturing a piece on the left diagonal
            if (getBoard().positionExists(leftDiagonalBlack) && getBoard().isThereAPieceAt(leftDiagonalBlack)
                    && checkPossibleCapture(leftDiagonalBlack)) {
                possibilities[leftDiagonalBlack.getRow()][leftDiagonalBlack.getColumn()] = true;
            }

            // Capturing a piece on the right diagonal
            if (getBoard().positionExists(rightDiagonalBlack) && getBoard().isThereAPieceAt(rightDiagonalBlack)
                    && checkPossibleCapture(rightDiagonalBlack)) {
                possibilities[rightDiagonalBlack.getRow()][rightDiagonalBlack.getColumn()] = true;
            }
        }
        return possibilities;
    }

    @Override
    public String toString() {
        return this.getColor() + "Pawn";
    }
}
