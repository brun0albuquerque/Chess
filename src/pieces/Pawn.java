package pieces;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.ChessPiece;

import java.util.Arrays;

public class Pawn extends ChessPiece {

    private final ChessMatch match;

    public Pawn(Board board, ChessColor chessColor, ChessMatch match) {
        super(board, chessColor);
        this.match = match;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibilities = new boolean[getBoard().getRows()][getBoard().getColumns()];

        for (int a = 0; a <= 7; a++) {
            for (int b = 0; b <= 7; b++) {
                System.out.print(getBoard().getPieceOnBoard(new Position(a, b)));
                if (getBoard().getPieceOnBoard(new Position(a, b)) != null) System.out.print("; " + getBoard().getPieceOnBoard(new Position(a, b)).getPosition());
                System.out.print("; " + possibilities[a][b]);
                System.out.println();
            }
            System.out.println();
        }

        if (getColor() == ChessColor.WHITE) {

            Position oneStepWhite = new Position(7 - getPosition().getRow(), getPosition().getColumn() + 1);
            Position twoStepsWhite = new Position(7 - getPosition().getRow(), getPosition().getColumn() + 2);
            Position leftDiagonalWhite = new Position(7 - getPosition().getRow() - 1, getPosition().getColumn() + 1);
            Position rightDiagonalWhite = new Position(7 - getPosition().getRow() + 1, getPosition().getColumn() + 1);

            // One house move
            if (getBoard().positionExists(oneStepWhite) && !getBoard().isThereAPieceAt(oneStepWhite)) {
                possibilities[oneStepWhite.getRow()][oneStepWhite.getColumn()] = true;
                System.out.println(possibilities[oneStepWhite.getRow()][oneStepWhite.getColumn()]);
            }

            // Two houses move
            if (getBoard().positionExists(oneStepWhite) && !getBoard().isThereAPieceAt(oneStepWhite)
                    && getBoard().positionExists(twoStepsWhite) && !getBoard().isThereAPieceAt(twoStepsWhite)
                    && getMoveCount() == 0) {
                possibilities[twoStepsWhite.getRow()][twoStepsWhite.getColumn()] = true;
                System.out.println(oneStepWhite.getRow() + ", " + oneStepWhite.getColumn() + "; " + possibilities[oneStepWhite.getRow()][oneStepWhite.getColumn()]);
                System.out.println(twoStepsWhite.getRow() + ", " + twoStepsWhite.getColumn() + "; " + possibilities[twoStepsWhite.getRow()][twoStepsWhite.getColumn()]);

            }

            // Capturing a piece on the left diagonal
            if (getBoard().positionExists(leftDiagonalWhite) && getBoard().isThereAPieceAt(leftDiagonalWhite)
                    && checkPossibleCapture(leftDiagonalWhite)) {
                possibilities[leftDiagonalWhite.getRow()][leftDiagonalWhite.getColumn()] = true;
                System.out.println(leftDiagonalWhite.getRow() + ", " + leftDiagonalWhite.getColumn() + "; " + possibilities[leftDiagonalWhite.getRow()][leftDiagonalWhite.getColumn()]);
            }

            // Capturing a piece on the right diagonal
            if (getBoard().positionExists(rightDiagonalWhite) && getBoard().isThereAPieceAt(rightDiagonalWhite)
                    && checkPossibleCapture(rightDiagonalWhite)) {
                possibilities[rightDiagonalWhite.getRow()][rightDiagonalWhite.getColumn()] = true;
                System.out.println(rightDiagonalWhite.getRow() + ", " + rightDiagonalWhite.getColumn() + "; " + possibilities[rightDiagonalWhite.getRow()][rightDiagonalWhite.getColumn()]);
            }
        }

        if (getColor() == ChessColor.BLACK) {

            Position oneStepBlack = new Position(7 - getPosition().getRow(), getPosition().getColumn() + 1);
            Position twoStepsBlack = new Position(7 - getPosition().getRow(), getPosition().getColumn() + 2);
            Position leftDiagonalBlack = new Position(7 - getPosition().getRow() + 1, getPosition().getColumn() + 1);
            Position rightDiagonalBlack = new Position(7 - getPosition().getRow() + 1, getPosition().getColumn() + 1);

            // One house move
            if (getBoard().positionExists(oneStepBlack) && !getBoard().isThereAPieceAt(oneStepBlack)) {
                possibilities[oneStepBlack.getRow()][oneStepBlack.getColumn()] = true;
                System.out.println(possibilities[oneStepBlack.getRow()][oneStepBlack.getColumn()]);
            }

            // Two houses move
            if (getBoard().positionExists(oneStepBlack) && !getBoard().isThereAPieceAt(oneStepBlack)
                    && getBoard().positionExists(twoStepsBlack) && !getBoard().isThereAPieceAt(twoStepsBlack)
                    && getMoveCount() == 0) {
                possibilities[twoStepsBlack.getRow()][twoStepsBlack.getColumn()] = true;
                System.out.println(oneStepBlack.getRow() + ", " + oneStepBlack.getColumn() + "; " + possibilities[oneStepBlack.getRow()][oneStepBlack.getColumn()]);
                System.out.println(twoStepsBlack.getRow() + ", " + twoStepsBlack.getColumn() + "; " + possibilities[twoStepsBlack.getRow()][twoStepsBlack.getColumn()]);
            }

            // Capturing a piece on the left diagonal
            if (getBoard().positionExists(leftDiagonalBlack) && getBoard().isThereAPieceAt(leftDiagonalBlack)
                    && checkPossibleCapture(leftDiagonalBlack)) {
                possibilities[leftDiagonalBlack.getRow()][leftDiagonalBlack.getColumn()] = true;
                System.out.println(leftDiagonalBlack.getRow() + ", " + leftDiagonalBlack.getColumn() + "; " + possibilities[leftDiagonalBlack.getRow()][leftDiagonalBlack.getColumn()]);
            }

            // Capturing a piece on the right diagonal
            if (getBoard().positionExists(rightDiagonalBlack) && getBoard().isThereAPieceAt(rightDiagonalBlack) &&
                    checkPossibleCapture(rightDiagonalBlack)) {
                possibilities[rightDiagonalBlack.getRow()][rightDiagonalBlack.getColumn()] = true;
                System.out.println(rightDiagonalBlack.getRow() + ", " + rightDiagonalBlack.getColumn() + "; " + possibilities[rightDiagonalBlack.getRow()][rightDiagonalBlack.getColumn()]);
            }
        }
        return possibilities;
    }

    @Override
    public String toString() {
        return this.getColor() + "Pawn";
    }
}
