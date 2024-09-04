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
        board = new Board(8,8);
    }

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

    private void validateSourcePosition(Position position){
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on the on the position.");
        }
            if (!board.piece(position).isThereAnyPossibleMove()) {
                throw new ChessException("There is no possible moves for the piece.");
            }
    }

    private Piece makeMove(Position source, Position target) {
        Piece sourcePiece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(sourcePiece, target);
        return capturedPiece;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }
}
