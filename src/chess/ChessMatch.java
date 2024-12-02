package chess;

import application.Sizes;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import pieces.*;

import javax.swing.*;

public class ChessMatch {
    private int turn;
    private final Board board;
    private ChessColor playerColor;

    public static boolean kingCheck;

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

    /* Validate if there is a piece at the position and if it's the same color as the player. */
    public boolean validateSourcePosition(Position position) {
        return board.isThereAPieceAt(position) && validatePieceColor(position);
    }

    /* Validate if there is a piece on the target position and if it's different color from the player. */
    public boolean validateTargetPosition(Position position) {
        if (board.isThereAPieceAt(position) && validatePieceColor(position))
            return false;
        return !board.isThereAPieceAt(position) || board.isThereAPieceAt(position) && !validatePieceColor(position);
    }

    /* Validate the position to make sure it has a piece from the opposite color to the player. */
    public boolean validateOpponentPiecePosition(Position position) {
        if (!board.isThereAPieceAt(position) || validateSourcePosition(position))
            return false;
        return board.isThereAPieceAt(position) && !validatePieceColor(position);
    }

    /* Checks if the first selected piece is an instance of King and the second is an instance of Rook. */
    public boolean validateCastlingPieces(Position source, Position target) {
        return (board.isThereAPieceAt(source) && board.getPieceOn(source) instanceof King
                && board.isThereAPieceAt(target) && board.getPieceOn(target) instanceof Rook)
                && !((ChessPiece) board.getPieceOn(source)).pieceMoved()
                && !((ChessPiece) board.getPieceOn(target)).pieceMoved();
    }

    /* Validates the pawn position and return true if it can be promoted. */
    public boolean validatePawnPromotion(Position position, ChessPiece piece) {
        return piece instanceof Pawn && piece.getPosition().getColumn() == 0
                || piece instanceof Pawn && piece.getPosition().getColumn() == 7;
    }

    /* Compare the color of the player and the piece. */
    public boolean validatePieceColor(Position position) {
        return playerColor == ((ChessPiece) board.getPieceOn(position)).getColor();
    }

    /* Promotes a pawn to a queen. */
    public void performPawnPromotion(Position position, ChessPiece piece) {
        board.removePiece(position);
        board.placePiece(position, new Queen(board, piece.getColor()));
    }

    /* Checks if the castling move is possible. */
    public boolean validateCastlingMove(Position kingPosition, Position rookPosition) {
        /* Validate if one of king or rook positions is null to prevent null pointer exception. */
        if (kingPosition == null || rookPosition == null)
            return false;

        /* If king position has an instance of King, and if the rook position has an instance of Rook.
        It prevents a class cast exception. */
        if (!validateCastlingPieces(kingPosition, rookPosition))
            return false;

        King king = (King) board.getPieceOn(kingPosition);
        Rook rook = (Rook) board.getPieceOn(rookPosition);

        /* Checks if the king or the rook are not null and if they have not yet moved. */
        if (king == null || rook == null || king.pieceMoved() || rook.pieceMoved() && validatePieceColor(rook.getPosition()))
            return false;

        /* Get the rook position based on the king position. */
        int step = (rookPosition.getRow() > kingPosition.getRow()) ? 1 : -1;

        /* Check if there is another piece between the king and the rook. */
        for (int row = kingPosition.getRow() + step; row != rookPosition.getRow(); row += step) {
            if (board.getPieceOn(new Position(row, kingPosition.getColumn())) != null)
                return false;
        }
        return true;
    }

    /* Changes the player turns. */
    public void nextTurn() {
        turn++;
        playerColor = invertColor(playerColor);
    }

    /* Inverts the color of the player. */
    private ChessColor invertColor(ChessColor chessColor) {
        return chessColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

    /* Load the pieces when the game starts. */
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

    /*  Checks if the king is in check, if it's checkmate or draw. */
    public boolean isKingInCheck(Position source, Position target) {
        Piece sourcePiece = board.getPieceOn(source);
        Piece targetPiece = board.getPieceOn(target);
        Position kingPosition = board.findKingOnBoard(getPlayerColor());

        if (kingPosition == null)
            throw new IllegalStateException();

        /* Check if the king is threatened before the move. */
        if (!validatePossibleCheck(kingPosition)) {
            return false;
        }

        /* Move the piece to target position. */
        board.removePiece(source);
        board.removePiece(target);
        board.placePiece(target, sourcePiece);

        /* If the moved piece is the king, it's necessary to update the king position. */
        kingPosition = sourcePiece instanceof King ? target : kingPosition;

        /* Iterates through the board to get the opponent piece's possible moves. */
        boolean check = validatePossibleCheck(kingPosition);

        if (check) {
            JOptionPane.showMessageDialog(null, "King is in check.",
                    "Chess", JOptionPane.INFORMATION_MESSAGE, null);
        }

        /* Undo the selected piece move. */
        board.removePiece(target);
        board.placePiece(source, sourcePiece);
        board.placePiece(target, targetPiece);

        return check;
    }

    /* Validate each opponent's pieces until it find any possible move that threatens the king's position. */
    private boolean validatePossibleCheck(Position kingPosition) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);

                /* If it's an opponent's piece, possibilities receive their possible moves. */
                if (this.validateOpponentPiecePosition(position)) {
                    boolean[][] possibilities = board.getPieceOn(position).possibleMoves(false);

                    /* If any piece movement matches the king's position, it returns true. */
                    if (possibilities[kingPosition.getRow()][kingPosition.getColumn()])
                        return true;
                }
            }
        }
        return false;
    }
}