package chess;

import application.Sizes;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private int turn;
    private boolean check;
    private boolean checkMate;
    private List<Piece> boardPieces;
    private ChessColor playerColor;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private final Board board;

    public ChessMatch() {
        this.board = new Board(Sizes.getBOARD_SIZE(), Sizes.getBOARD_SIZE());
        this.playerColor = ChessColor.WHITE;
        this.boardPieces = new ArrayList<>();
        this.turn = 1;
        loadInitialPieces();
        invertMatrix();
    }

    public Board getBoard() {
        return board;
    }

    public int getTurn() {
        return turn;
    }

    public List<Piece> getBoardPieces() {
        return boardPieces;
    }

    public ChessColor getPlayerColor() {
        return playerColor;
    }

    // Get the position of all pieces on the board
    public ChessPiece[][] getPieces() {
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int a = 0; a < board.getRows(); a++) {
            for (int b = 0; b < board.getColumns(); b++) {
                Position position = new Position(a, b);
                matrix[a][b] = (ChessPiece) board.getPieceOn(position);
            }
        }
        return matrix;
    }

    // Validate the position and if there is a movement for the piece
    public boolean validateSourcePosition(Position position) {
        return board.isThereAPieceAt(position) && validatePieceColor(position);
    }

    public boolean validateTargetPosition(Position position) {
        if (board.isThereAPieceAt(position) && validatePieceColor(position)) {
            return false;
        }
        return !board.isThereAPieceAt(position);
    }

    // Validate if the piece color is the same as the player
    public boolean validatePieceColor(Position position) {
        return playerColor == ((ChessPiece) board.getPieceOn(position)).getColor();
    }

    // Change the player turn
    public void nextTurn() {
        turn++;
        playerColor = invertColor(playerColor);
    }

    private ChessColor invertColor(ChessColor chessColor) {
        return chessColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

    // Place a piece on the board
    private void placePiece(int row, int col, ChessPiece piece) {
        board.placePiece(new Position(col, row), piece);
        boardPieces.add(piece);
    }

    private void loadInitialPieces() {
        placePiece(0, 0, new Rook(board, ChessColor.WHITE));
        placePiece(0, 1, new Knight(board, ChessColor.WHITE));
        placePiece(0, 2, new Bishop(board, ChessColor.WHITE));
        placePiece(0, 3, new King(board, ChessColor.WHITE, this));
        placePiece(0, 4, new Queen(board, ChessColor.WHITE));
        placePiece(0, 5, new Bishop(board, ChessColor.WHITE));
        placePiece(0, 6, new Knight(board, ChessColor.WHITE));
        placePiece(0, 7, new Rook(board, ChessColor.WHITE));

        for (int a = 0; a < 8; a++) {
            placePiece(1, a, new Pawn(board, ChessColor.WHITE, this));
        }

        placePiece(7, 0, new Rook(board, ChessColor.BLACK));
        placePiece(7, 1, new Knight(board, ChessColor.BLACK));
        placePiece(7, 2, new Bishop(board, ChessColor.BLACK));
        placePiece(7, 3, new King(board, ChessColor.BLACK, this));
        placePiece(7, 4, new Queen(board, ChessColor.BLACK));
        placePiece(7, 5, new Bishop(board, ChessColor.BLACK));
        placePiece(7, 6, new Knight(board, ChessColor.BLACK));
        placePiece(7, 7, new Rook(board, ChessColor.BLACK));

        for (int a = 0; a < 8; a++) {
            placePiece(6, a, new Pawn(board, ChessColor.BLACK, this));
        }
    }

    private void invertMatrix() {
        Piece[][] matrix = new Piece[8][8];
        for (int a = 7; a >= 0; a--) {
            for (int b = 0; b <= 7; b++) {
                matrix[a][7 - b] = board.getBoardPieces()[a][b];
            }
        }
        board.setBoardPieces(matrix);
    }
}
