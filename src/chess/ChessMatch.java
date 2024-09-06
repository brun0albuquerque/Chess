package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public class ChessMatch {
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean isCheckMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private final Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    // Get the position of all pieces on the board
    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int a = 0; a < board.getRows(); a++) {
            for (int b = 0; b < board.getColumns(); b++) {
                Position position = new Position(a, b);
                matrix[a][b] = (ChessPiece) board.piece(position);
            }
        }
        return matrix;
    }

    // Validate the position and if there is a movement for the piece
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("No piece on the position.");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("Cannot move this piece.");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("No possible moves for the piece.");
        }

    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    // Make the action of move a piece from a position to another (change the positions in board)
    private Piece makeMove(Position source, Position target) {
        Piece sourcePiece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(sourcePiece, target);
        return capturedPiece;
    }

    public boolean[][] possibleMoves(Position sourcePosition) {
        validateSourcePosition(sourcePosition);
        return board.piece(sourcePosition).possibleMoves();
    }

    // Change the position of a piece and make the capture of an opponent piece
    public ChessPiece performChessMove(Position sourcePosition, Position targetPosition) {
        validateSourcePosition(sourcePosition);
        Piece capturedPiece = makeMove(sourcePosition, targetPosition);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    // Place a piece on the board
    private void placeNewPiece(ChessPiece piece, int column, int row) {
        board.placePiece(piece, new Position(column, row));
    }
}
