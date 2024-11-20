package pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.ChessPiece;

public class Pawn extends ChessPiece {

    private final ChessMatch match;

    public Pawn(Board board, ChessColor chessColor, ChessMatch match) {
        super(board, chessColor);
        this.match = match;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        if (getColor() == ChessColor.WHITE) {

            Position oneStepWhite = new Position(getPosition().getRow() + 1, 7 - getPosition().getColumn());
            Position twoStepsWhite = new Position(getPosition().getRow() + 2, 7 - getPosition().getColumn());
            Position leftDiagonalWhite = new Position(getPosition().getRow() + 1,  7 - getPosition().getColumn() + 1);
            Position rightDiagonalWhite = new Position(getPosition().getRow() + 1, 7 - getPosition().getColumn() - 1);

            System.out.println("One: " + oneStepWhite + ", Two: " + twoStepsWhite);

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

            Position oneStepBlack = new Position(getPosition().getRow() - 1, 7 - getPosition().getColumn());
            Position twoStepsBlack = new Position(getPosition().getRow() - 2, 7 - getPosition().getColumn());
            Position leftDiagonalBlack = new Position(getPosition().getRow() - 1, 7 - getPosition().getColumn() + 1);
            Position rightDiagonalBlack = new Position(getPosition().getRow() - 1, 7 - getPosition().getColumn() - 1);

            System.out.println("One: " + oneStepBlack + ", Two: " + twoStepsBlack);

            // One house move
            if (!getBoard().isThereAPieceAt(oneStepBlack)) {
                possibilities[oneStepBlack.getRow()][oneStepBlack.getColumn()] = true;
            }

            // Two houses move
            if (!getBoard().isThereAPieceAt(oneStepBlack) && !getBoard().isThereAPieceAt(twoStepsBlack)
                    && getMoveCount() == 0) {
                possibilities[twoStepsBlack.getRow()][twoStepsBlack.getColumn()] = true;
            }

            // Capturing a piece on the left diagonal
            if (getBoard().isThereAPieceAt(leftDiagonalBlack) && checkPossibleCapture(leftDiagonalBlack)) {
                possibilities[leftDiagonalBlack.getRow()][leftDiagonalBlack.getColumn()] = true;
            }

            // Capturing a piece on the right diagonal
            if (getBoard().isThereAPieceAt(rightDiagonalBlack) && checkPossibleCapture(rightDiagonalBlack)) {
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
