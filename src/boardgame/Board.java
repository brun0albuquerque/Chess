package boardgame;

import javax.swing.*;

public class Board {
    private final int columns;
    private final int rows;
    private final Piece[][] boardPieces;

    public Board(int columns, int rows) {
        // Checks if rows and columns are positive
        if (columns != 8 || rows != 8) {
            JOptionPane.showMessageDialog(null, "Error when try to create the board. The game can't start.",
                    "Board error", JOptionPane.INFORMATION_MESSAGE, null);
        }
        this.columns = columns;
        this.rows = rows;
        this.boardPieces = new Piece[columns][rows]; // This matrix represents the board itself, it's content is all pieces positions
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    // Change the piece position if the position is valid
    public Piece pieceOnBoard(Position position) {
        if (!positionExists(position)) {
            JOptionPane.showMessageDialog(null, "Position is not on the board.",
                    "Position error", JOptionPane.INFORMATION_MESSAGE, null);
        }
        return boardPieces[position.getColumn()][position.getRow()];
    }

    // Check if there is a piece on the board position and if not, place a piece in the matrix position
    public void placePiece(Position position, Piece piece) {
        if (thereIsAPiece(position)) {
            JOptionPane.showMessageDialog(null, "There is already a piece on the position.",
                    "Position error", JOptionPane.INFORMATION_MESSAGE, null);
        }
        boardPieces[position.getColumn()][position.getRow()] = piece;
        piece.setPosition(position);
    }

    /* Check if the position is valid and if there is a piece on the board position, if it has a piece,
    then remove the piece from the board position */
    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            JOptionPane.showMessageDialog(null, "Position is not on the board.",
                    "Position error", JOptionPane.INFORMATION_MESSAGE, null);
        }
        if (pieceOnBoard(position) == null) {
            return null;
        }
        Piece removedPiece = pieceOnBoard(position);
        removedPiece.setPosition(null);
        boardPieces[position.getColumn()][position.getRow()] = null;
        return removedPiece;
    }

    // Check if there is a piece on the board position
    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            JOptionPane.showMessageDialog(null, "There is no piece at " + position,
                    "Position error", JOptionPane.INFORMATION_MESSAGE, null);

            return false;
        }
        return pieceOnBoard(position) != null;
    }

    // Check if the position is positive and less than the board size
    public boolean positionExists(Position position) {
        return position.getColumn() >= 0 && position.getColumn() < this.columns
                && position.getRow() >= 0 && position.getRow() < this.rows;
    }
}
