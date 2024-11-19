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

        Position oneStepPositionWhite = new Position(getPosition().getRow() + 1, getPosition().getColumn());
        Position twoStepsPositionWhite = new Position(getPosition().getRow() + 2, getPosition().getColumn());
        Position oneStepPositionBlack = new Position(getPosition().getRow() - 1, getPosition().getColumn());
        Position twoStepsPositionBlack = new Position(getPosition().getRow() - 2, getPosition().getColumn());

        Position leftDiagonalPositionWhite = new Position(getPosition().getRow() + 1, getPosition().getColumn() + 1);
        Position rightDiagonalPositionWhite = new Position(getPosition().getRow() + 1, getPosition().getColumn() - 1);
        Position leftDiagonalPositionBlack = new Position(getPosition().getRow() - 1, getPosition().getColumn() + 1);
        Position rightDiagonalPositionBlack = new Position(getPosition().getRow() - 1, getPosition().getColumn() - 1);

        if (getColor() == ChessColor.WHITE) {

            // One house move
            if (getBoard().positionExists(oneStepPositionWhite) && !getBoard().isThereAPieceAt(oneStepPositionWhite)) {
                possibilities[oneStepPositionWhite.getRow()][oneStepPositionWhite.getColumn()] = true;
            }

            // Two houses move
            if (getBoard().positionExists(oneStepPositionWhite) && !getBoard().isThereAPieceAt(oneStepPositionWhite)
                    && getBoard().positionExists(twoStepsPositionWhite) && !getBoard().isThereAPieceAt(twoStepsPositionWhite)
                    && getMoveCount() == 0) {
                possibilities[twoStepsPositionWhite.getRow()][twoStepsPositionWhite.getColumn()] = true;
            }

            // Capturing a piece on the left diagonal
            if (getBoard().positionExists(leftDiagonalPositionWhite) && getBoard().isThereAPieceAt(leftDiagonalPositionWhite)
                    && checkPossibleCapture(leftDiagonalPositionWhite)) {
                possibilities[leftDiagonalPositionWhite.getRow()][leftDiagonalPositionWhite.getColumn()] = true;
            }

            // Capturing a piece on the right diagonal
            if (getBoard().positionExists(rightDiagonalPositionWhite) && getBoard().isThereAPieceAt(rightDiagonalPositionWhite)
                    && checkPossibleCapture(rightDiagonalPositionWhite)) {
                possibilities[rightDiagonalPositionWhite.getRow()][rightDiagonalPositionWhite.getColumn()] = true;
            }
        }

        if (getColor() == ChessColor.BLACK) {

            // One house move
            if (!getBoard().isThereAPieceAt(oneStepPositionBlack)) {
                possibilities[oneStepPositionBlack.getRow()][oneStepPositionBlack.getColumn()] = true;
            }

            // Two houses move
            if (!getBoard().isThereAPieceAt(oneStepPositionBlack) && !getBoard().isThereAPieceAt(twoStepsPositionBlack)
                    && getMoveCount() == 0) {
                possibilities[twoStepsPositionBlack.getRow()][twoStepsPositionBlack.getColumn()] = true;
            }

            // Capturing a piece on the left diagonal
            if (getBoard().isThereAPieceAt(leftDiagonalPositionBlack) && checkPossibleCapture(leftDiagonalPositionBlack)) {
                possibilities[leftDiagonalPositionBlack.getRow()][leftDiagonalPositionBlack.getColumn()] = true;
            }

            // Capturing a piece on the right diagonal
            if (getBoard().isThereAPieceAt(rightDiagonalPositionBlack) && checkPossibleCapture(rightDiagonalPositionBlack)) {
                possibilities[rightDiagonalPositionBlack.getRow()][rightDiagonalPositionBlack.getColumn()] = true;
            }
        }
        return possibilities;
    }

    @Override
    public String toString() {
        return this.getColor() + "Pawn";
    }
}
