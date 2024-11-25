package boardgame;

import chess.ChessPiece;

import javax.swing.*;

public class Board {
    private final int columns;
    private final int rows;
    private Piece[][] boardPieces;

    public Board(int rows, int columns) {
        // Checks if rows and columns are positive
        if (rows != 8 || columns != 8) {
            JOptionPane.showMessageDialog(null, "Error board creation. The game can't start.",
                    "Board error", JOptionPane.INFORMATION_MESSAGE, null);
            System.exit(1);
        }
        this.rows = rows;
        this.columns = columns;
        this.boardPieces = new Piece[rows][columns]; // This matrix represents the board itself, it's content is all pieces positions
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

    public void setBoardPieces(Piece[][] boardPieces) {
        this.boardPieces = boardPieces;
    }

    // Get the piece position, if the position is valid
    public Piece getPieceOn(Position position) {
        if (!positionExists(position)) {
            return null;
        }
        return boardPieces[position.getRow()][position.getColumn()];
    }

    // Check if there is a piece on the board position and if not, place a piece in the matrix position
    public void placePiece(Position position, Piece piece) {
        if (isThereAPieceAt(position)) {
            return;
        }
        boardPieces[position.getRow()][position.getColumn()] = piece;
        piece.setPosition(position);
    }

    public void placePiece(int x, int y, ChessPiece piece) {
        Position position = new Position(x, y);
        if (isThereAPieceAt(position)) {
            return;
        }
        boardPieces[position.getRow()][position.getColumn()] = piece;
        piece.setPosition(position);
    }

    /* Check if the position is valid and if there is a piece on the board position, if it has a piece,
    then remove the piece from the board position */
    public void removePiece(Position position) {
        if (getPieceOn(position) == null) {
            return;
        }
        Piece piece = getPieceOn(position);
        piece.setPosition(null);
        boardPieces[position.getRow()][position.getColumn()] = null;
    }

    // Check if the position is positive and less than the board size
    public boolean positionExists(Position position) {
        return position.getRow() >= 0 && position.getRow() < this.rows
                && position.getColumn() >= 0 && position.getColumn() < this.columns;
    }

    // Check if there is a piece on the board position
    public boolean isThereAPieceAt(Position position) {
        if (!positionExists(position)) {
            return false;
        }
        return getPieceOn(position) != null;
    }
}
