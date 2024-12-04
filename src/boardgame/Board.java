package boardgame;

import chess.ChessColor;
import pieces.King;

import javax.swing.*;

public class Board {
    private final int columns;
    private final int rows;
    private final Piece[][] boardPieces;

    public Board(int rows, int columns) {
        /* Checks if rows and columns are positive. */
        if (rows != 8 || columns != 8) {
            JOptionPane.showMessageDialog(null, "Error board creation. The game can't start.",
                    "Board error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }
        this.rows = rows;
        this.columns = columns;
        this.boardPieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece[][] getBoardPieces() {
        return boardPieces;
    }

    /**
     * Return the piece position if it is a valid position.
     */
    public Piece getPiece(Position position) {
        if (!positionExists(position))
            return null;

        return boardPieces[position.getRow()][position.getColumn()];
    }

    /**
     * Check if there is a piece on the position and if not, place a piece in the matrix position and set the
     * piece position.
     * @param position the position to place the piece.
     * @param piece the piece to be placed on the board.
     */
    public void placePiece(Position position, Piece piece) {
        if (isPositionOccupied(position))
            return;

        boardPieces[position.getRow()][position.getColumn()] = piece;
        piece.setPosition(position);
    }

    /**
     * If there is a piece on the board position, remove the piece from the board position and set the piece position
     * null.
     * @param position the position of the piece to be removed.
     */
    public void removePiece(Position position) {
        if (getPiece(position) == null)
            return;

        Piece piece = getPiece(position);
        piece.setPosition(null);
        boardPieces[position.getRow()][position.getColumn()] = null;
    }

    /**
     * Checks if the position is within the board limits.
     * @param position is the position to be checked.
     * @return true if the position is within the board bounds.
     */
    public boolean positionExists(Position position) {
        return position.getRow() >= 0 && position.getRow() < this.rows
                && position.getColumn() >= 0 && position.getColumn() < this.columns;
    }

    /**
     * Check if there is a piece on the position.
     * @param position is the position to be checked.
     * @return true if the position has a piece.
     */
    public boolean isPositionOccupied(Position position) {
        if (!positionExists(position))
            return false;

        return getPiece(position) != null;
    }

    /**
     * Search the king instance on the board and returns its position.
     * @param color the player's color.
     * @return the king's position.
     */
    public Position getKingPosition(ChessColor color) {
        Position position;

        for (Piece[] boardRow : boardPieces) {
            for (Piece piece : boardRow) {

                if (piece != null) {
                    position = piece.getPosition();

                    /* Only returns the king position if it's the same color as the player. */
                    if (piece instanceof King && ((King) piece).getColor() == color)
                        return position;
                }
            }
        }
        return null;
    }
}