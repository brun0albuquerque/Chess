package boardgame;

import chess.ChessColor;
import chess.KingNotFoundException;
import pieces.King;
import util.Util;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;

public class Board {
    private final int rows;
    private final int columns;
    private final Piece[][] boardPieces;

    private final ArrayList<Piece> activePieces;
    private final ArrayList<Piece> capturedPieces;

    public Board(int rows, int columns) {

        /* Checks if rows and columns are positive. */
        if (rows != 8 || columns != 8) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error board creation. The game can't start.",
                    "Board error",
                    JOptionPane.ERROR_MESSAGE,
                    null);
            System.exit(1);
        }
        this.rows = rows;
        this.columns = columns;
        this.boardPieces = new Piece[rows][columns];
        this.activePieces = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
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

    public ArrayList<Piece> getActivePieces() {
        return activePieces;
    }

    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
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
     * Check if there is a piece on the position and if not, place a piece
     * in the matrix position and set the piece position.
     * @param position the position to place the piece.
     * @param piece the piece to be placed on the board.
     */
    public void placePiece(Position position, Piece piece) {
        if (!isPositionEmpty(position))
            return;

        boardPieces[position.getRow()][position.getColumn()] = piece;
        piece.setPosition(position);
        activePieces.add(piece);
    }

    /**
     * If there is a piece on the board position, remove the piece from
     * the board position and set the piece position null.
     * @param position the position of the piece to be removed.
     */
    public void removePiece(Position position) {
        if (Util.isObjectNull(getPiece(position)))
            return;

        Piece piece = getPiece(position);
        piece.setPosition(null);
        boardPieces[position.getRow()][position.getColumn()] = null;
        activePieces.remove(piece);
        capturedPieces.add(piece);
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
    public boolean isPositionEmpty(Position position) {
        if (!positionExists(position))
            throw new IndexOutOfBoundsException("Position is not on the board.");

        return Util.isObjectNull(getPiece(position));
    }

    /**
     * Get the king's position on the board.
     * @param color the player's color.
     * @return the king's position.
     */
    public Position getKingPosition(ChessColor color) throws KingNotFoundException {
        Optional<Piece> optionalKing = (activePieces.stream()
                .filter(piece -> piece instanceof King &&
                        ((King) piece).getColor().equals(color)).findFirst());
        return optionalKing.orElseThrow(KingNotFoundException::new).getPosition();
    }
}