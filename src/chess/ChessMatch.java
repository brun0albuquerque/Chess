package chess;

import application.Sizes;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private final Board board;
    private ChessColor currentPlayer;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private boolean check;
    private boolean isCheckMate;
    private int turn;
    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces;

    public ChessMatch(Sizes sizes) {
        this.piecesOnTheBoard = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
        this.board = new Board(sizes.getBOARD_SIZE(), sizes.getBOARD_SIZE());
        this.currentPlayer = ChessColor.WHITE;
        this.turn = 1;
        loadInitialPieces();
    }

    public int getTurn() {
        return turn;
    }

    public ChessColor getCurrentPlayer() {
        return currentPlayer;
    }

    // Get the position of all pieces on the board
    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int a = 0; a < board.getRows(); a++) {
            for (int b = 0; b < board.getColumns(); b++) {
                Position position = new Position(a, b);
                matrix[a][b] = (ChessPiece) board.pieceOnBoard(position);
            }
        }
        return matrix;
    }

    // Validate the position and if there is a movement for the piece
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("No piece on the position.");
        }
        if (currentPlayer != ((ChessPiece) board.pieceOnBoard(position)).getColor()) {
            throw new ChessException("Cannot move this piece.");
        }
        if (!board.pieceOnBoard(position).isThereAnyPossibleMove()) {
            throw new ChessException("No possible moves for the piece.");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.pieceOnBoard(source).possiblePieceMoves(target)) {
            throw new ChessException("Cannot move to this position.");
        }
    }

    // Change the player turn
    private void nextTurn() {
        turn++;
        currentPlayer = currentPlayer == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

    // Make the action of move a piece from a position to another (change the positions in board)
    private Piece movePiece(Position source, Position target) {
        ChessPiece sourcePiece = (ChessPiece) board.removePiece(source);
        sourcePiece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        board.placePiece(target, sourcePiece);
        return capturedPiece;
    }

    // Get the possible moves of the selected piece on the board
    public boolean[][] possibleMoves(Position position) {
        validateSourcePosition(position);
        return board.pieceOnBoard(position).possibleMoves();
    }

    // Change the position of a piece and make the capture of an opponent piece
    public ChessPiece performChessMove(Position source, Position target) {
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece selectedPiece = movePiece(source, target);
        ChessPiece piece = (ChessPiece) selectedPiece;
        piece.increaseMoveCount();
        nextTurn();
        return piece;
    }

    private ChessColor invertColor(ChessColor chessColor) {
        return (chessColor == ChessColor.WHITE) ? ChessColor.BLACK : ChessColor.WHITE;
    }

    // Place a piece on the board
    private void placePiece(int row, int col, ChessPiece piece) {
        board.placePiece(new Position(row, col), piece);
        piecesOnTheBoard.add(piece);
    }

    private void loadInitialPieces() {
        placePiece(0, 0, new Rook(board, ChessColor.WHITE));
        placePiece(0, 1, new Knight(board, ChessColor.WHITE));
        placePiece(0, 2, new Bishop(board, ChessColor.WHITE));
        placePiece(0, 3, new Queen(board, ChessColor.WHITE));
        placePiece(0, 4, new King(board, ChessColor.WHITE, this));
        placePiece(0, 5, new Bishop(board, ChessColor.WHITE));
        placePiece(0, 6, new Knight(board, ChessColor.WHITE));
        placePiece(0, 7, new Rook(board, ChessColor.WHITE));

        for (int a = 0; a < 8; a++) {
            placePiece(1, a, new Pawn(board, ChessColor.WHITE, this));
        }

        placePiece(7, 0, new Rook(board, ChessColor.BLACK));
        placePiece(7, 1, new Knight(board, ChessColor.BLACK));
        placePiece(7, 2, new Bishop(board, ChessColor.BLACK));
        placePiece(7, 3, new Queen(board, ChessColor.BLACK));
        placePiece(7, 4, new King(board, ChessColor.BLACK, this));
        placePiece(7, 5, new Bishop(board, ChessColor.BLACK));
        placePiece(7, 6, new Knight(board, ChessColor.BLACK));
        placePiece(7, 7, new Rook(board, ChessColor.BLACK));

        for (int a = 0; a < 8; a++) {
            placePiece(6, a, new Pawn(board, ChessColor.BLACK, this));
        }
    }
}
