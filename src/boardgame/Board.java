package boardgame;

public class Board {
    private final Integer rows;
    private final Integer columns;
    private final Piece[][] boardPieces;

    public Board(Integer rows, Integer columns) {
        // Checks if rows and columns are positive
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: there must be at least a row and a column.");
        }
        this.rows = rows;
        this.columns = columns;
        boardPieces = new Piece[rows][columns]; // This matrix represents the board itself, it's content is all pieces positions
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getColumns() {
        return columns;
    }

    // Change the piece position if the position is valid
    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position is not on the board.");
        }
        return boardPieces[position.getRow()][position.getColumn()];
    }

    // Check if there is a piece on the board position and if not, place a piece in the matrix position
    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }
        boardPieces[position.getRow()][position.getColumn()] = piece;
        piece.setPosition(position);
    }

    // Check if the position is valid and if there is a piece on the board position, if it has a piece, then remove the piece from the board position
    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board.");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece removedPiece = piece(position);
        removedPiece.setPosition(null);
        boardPieces[position.getRow()][position.getColumn()] = null;
        return removedPiece;
    }

    // Check if there is a piece on the board position
    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position is not on the board.");
        }
        return piece(position) != null;
    }

    // Check if the position is positive and less than 8 (number of rows and columns on the board)
    public boolean positionExists(Position position) {
        return position.getRow() >= 0 && position.getRow() < this.rows
                && position.getColumn() >= 0 && position.getColumn() < this.columns;
    }
}
