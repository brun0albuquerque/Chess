package chess;

import application.Sizes;
import boardgame.Board;
import boardgame.Position;
import pieces.*;

public class ChessMatch {
    private int turn;
    private boolean check;
    private boolean checkMate;
    private ChessColor playerColor;
    private ChessPiece enPassantVulnerable;
    private final Board board;

    public ChessMatch() {
        this.board = new Board(Sizes.getBOARD_SIZE(), Sizes.getBOARD_SIZE());
        this.playerColor = ChessColor.WHITE;
        this.turn = 0;
        loadInitialPieces();
    }

    public Board getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }

    public ChessColor getPlayerColor() {
        return playerColor;
    }

    // Get the position of all pieces on the board
    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[8][board.getColumns()];
        for (int a = 0; a < board.getRows(); a++) {
            for (int b = 0; b < board.getColumns(); b++) {
                Position position = new Position(a, b);
                matrix[a][b] = (ChessPiece) board.getPieceOn(position);
            }
        }
        return matrix;
    }

    // Validates if there is a movement for the piece on the source position
    public boolean validateSourcePosition(Position position) {
        return board.isThereAPieceAt(position) && validatePieceColor(position);
    }

    // Validates if there is a piece on the target position and check if it's not the same color as the player
    public boolean validateTargetPosition(Position position) {
        if (board.isThereAPieceAt(position) && validatePieceColor(position)) {
            return false;
        }
        return !board.isThereAPieceAt(position) || board.isThereAPieceAt(position) && !validatePieceColor(position);
    }

    // Compare the color of the player and the piece
    public boolean validatePieceColor(Position position) {
        return playerColor == ((ChessPiece) board.getPieceOn(position)).getColor();
    }

    // Changes the player turn
    public void nextTurn() {
        turn++;
        playerColor = invertColor(playerColor);
    }

    // Inverts the color of the player
    private ChessColor invertColor(ChessColor chessColor) {
        return chessColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

    // Load the pieces before the game starts
    private void loadInitialPieces() {
        board.placePiece(new Position(0, 7), new Rook(board, ChessColor.WHITE));
        board.placePiece(new Position(1, 7), new Knight(board, ChessColor.WHITE));
        board.placePiece(new Position(2, 7), new Bishop(board, ChessColor.WHITE));
        board.placePiece(new Position(3, 7), new King(board, ChessColor.WHITE, this));
        board.placePiece(new Position(4, 7), new Queen(board, ChessColor.WHITE));
        board.placePiece(new Position(5, 7), new Bishop(board, ChessColor.WHITE));
        board.placePiece(new Position(6, 7), new Knight(board, ChessColor.WHITE));
        board.placePiece(new Position(7, 7), new Rook(board, ChessColor.WHITE));

        for (int a = 0; a <= 7; a++) {
            board.placePiece(new Position(a, 6), new Pawn(board, ChessColor.WHITE, this));
        }

        board.placePiece(new Position(0, 0), new Rook(board, ChessColor.BLACK));
        board.placePiece(new Position(1, 0), new Knight(board, ChessColor.BLACK));
        board.placePiece(new Position(2, 0), new Bishop(board, ChessColor.BLACK));
        board.placePiece(new Position(3, 0), new King(board, ChessColor.BLACK, this));
        board.placePiece(new Position(4, 0), new Queen(board, ChessColor.BLACK));
        board.placePiece(new Position(5, 0), new Bishop(board, ChessColor.BLACK));
        board.placePiece(new Position(6, 0), new Knight(board, ChessColor.BLACK));
        board.placePiece(new Position(7, 0), new Rook(board, ChessColor.BLACK));

        for (int a = 0; a <= 7; a++) {
            board.placePiece(new Position(a, 1), new Pawn(board, ChessColor.BLACK, this));
        }
    }
}
