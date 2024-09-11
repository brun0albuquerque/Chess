package chess;

import application.Sizes;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private int turn;
    private boolean check;
    private boolean isCheckMate;
    private Color currentPlayer;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private final Board board;
    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces;

    public ChessMatch(Sizes sizes) {
        this.piecesOnTheBoard = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
        this.board = new Board(sizes.getBOARD_SIZE(), sizes.getBOARD_SIZE());
        this.currentPlayer = Color.WHITE;
        this.turn = 1;
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

    // Change the player turn
    private void nextTurn() {
        turn++;
        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private Color invertColor(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
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

    // Make the action of move a piece from a position to another (change the positions in board)
    private Piece makeMove(Position source, Position target) {
        Piece sourcePiece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        board.placePiece(sourcePiece, target);
        return capturedPiece;
    }

    public boolean[][] possibleMoves(Position position) {
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    // Change the position of a piece and make the capture of an opponent piece
    public ChessPiece performChessMove(Position sourcePosition, Position targetPosition) {
        validateSourcePosition(sourcePosition);
        Piece selectedPiece = makeMove(sourcePosition, targetPosition);
        nextTurn();
        ChessPiece piece = (ChessPiece) selectedPiece;
        piece.increaseMoveCount();
        System.out.println(piece.getMoveCount());
        return piece;
    }

    // Place a piece on the board
    private void placeNewPiece(int row, int col, ChessPiece piece) {
        board.placePiece(piece, new Position(row, col));
        piecesOnTheBoard.add(piece);
    }
}
