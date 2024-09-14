package boardgame;

public class Board {
    private final int columns;
    private final int rows;
    private final Piece[][] boardPieces;

    public Board(int columns, int rows) {
        // Checks if rows and columns are positive
        if (columns != 8 || rows != 8) {
            throw new BoardException("Error creating board.");
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
            throw new BoardException("Position is not on the board.");
        }
        return boardPieces[position.getColumn()][position.getRow()];
    }

    // Check if there is a piece on the board position and if not, place a piece in the matrix position
    public void placePiece(Position position, Piece piece) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position.");
        }
        boardPieces[position.getColumn()][position.getRow()] = piece;
        piece.setPosition(position);
    }

    /* Check if the position is valid and if there is a piece on the board position, if it has a piece,
    then remove the piece from the board position */
    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board.");
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
            System.out.println("No piece found at: " + position);
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
