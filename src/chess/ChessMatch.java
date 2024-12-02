package chess;

import application.Sizes;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import controller.InputController;
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

    /* Changes the player turns. */
    public void nextTurn() {
        turn++;
        playerColor = invertColor(playerColor);
    }

    /* Inverts the color of the player. */
    private ChessColor invertColor(ChessColor chessColor) {
        return chessColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

    /* Validation methods. Each method of validation will authenticate a condition from the game. */

    /* Validate if there is a piece at the position and if it's the same color as the player. */
    public boolean validateSourcePosition(Position position) {
        return board.isThereAPieceAt(position) && validatePieceColor(position);
    }

    /* Validate if there is an opponent piece at the position. */
    public boolean validateTargetPosition(Position position) {
        if (board.isThereAPieceAt(position) && validatePieceColor(position))
            return false;
        return !board.isThereAPieceAt(position) || board.isThereAPieceAt(position) && !validatePieceColor(position);
    }

    /*
     * It seems to be the same as validate target position, but it isn't.
     * Excludes any empty position and ally piece.
     * This method only returns true if there is a piece in a given position, and it is opponent's.
     */
    public boolean validateOpponentPiecePosition(Position position) {
        if (!board.isThereAPieceAt(position) || validateSourcePosition(position))
            return false;
        return board.isThereAPieceAt(position) && !validatePieceColor(position);
    }

    /*
     * To validate the castle move, you need to be sure about the pieces.
     * This method makes sure the first piece is an instance of king and the second is an instance of rook and
     * if they haven't moved yet.
     */
    public boolean validateCastlingPieces(Position source, Position target) {
        return (board.isThereAPieceAt(source) && board.getPieceOn(source) instanceof King
                && board.isThereAPieceAt(target) && board.getPieceOn(target) instanceof Rook)
                && !((ChessPiece) board.getPieceOn(source)).pieceMoved()
                && !((ChessPiece) board.getPieceOn(target)).pieceMoved();
    }

    /*
     * Since the pawns don't start at the first row on their side of the board, when a pawn reaches the last
     * row, they can be promoted.
     * Only need to check if they are in the last row.
     */
    public boolean validatePawnPromotion(ChessPiece piece) {
        return piece instanceof Pawn && piece.getPosition().getColumn() == 0
                || piece instanceof Pawn && piece.getPosition().getColumn() == 7;
    }

    /* Compare the color of the player and the piece. */
    public boolean validatePieceColor(Position position) {
        return playerColor == ((ChessPiece) board.getPieceOn(position)).getColor();
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

    /* Validate each opponent's pieces until it find any possible move that threatens the king's position. */
    public boolean validatePossibleCheck(Position kingPosition) {
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

    /* Movements and special moves methods. */

    /* Perform a normal move on the board. */
    public void performPieceMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) getBoard().getPieceOn(source); /* Get the piece from the board. */

        /* Remove both source and target pieces from the board. */
        getBoard().removePiece(source);
        getBoard().removePiece(target);

        /* Place the source piece on target position. */
        getBoard().placePiece(target, piece);

        /* Add the move counter by one. */
        piece.addMoveCount();

        /* Change to the next turn. */
        nextTurn();
    }

    public void undoPieceMove(Position source, Position target) {
        Piece sourcePiece = board.getPieceOn(source);
        Piece targetPiece = board.getPieceOn(target);

        /* Undo the selected piece move. */
        board.removePiece(target);
        board.placePiece(source, sourcePiece);
        board.placePiece(target, targetPiece);
    }

    /*
     * This method promotes a pawn to a queen.
     * I chose to always promote a pawn to a new queen because it's the most valuable and powerful piece.
     * For now, the pawn can only be promoted to a queen.
     * */
    public void performPawnPromotion(Position position, ChessPiece piece) {
        board.removePiece(position);
        board.placePiece(position, new Queen(board, piece.getColor()));
    }

    public void enPassant() {

    }

    /*
     * This method will search and get the king's position on the board and verify if the king is in check.
     * If the king is in check and has any valid move, he will only allow the move that takes him out of check.
     */
    public void isKingInCheck(Position source, Position target) {
        Piece sourcePiece = board.getPieceOn(source);
        Piece targetPiece = board.getPieceOn(target);
        Position kingPosition = board.findKingOnBoard(getPlayerColor());

        if (kingPosition == null)
            throw new IllegalStateException();

        /* Check if the king is threatened before the move. */
        if (validatePossibleCheck(kingPosition)) {
            ChessMatch.kingCheck = true;
        } else {
            return;
        }

        /* Move the piece to target position. */
        board.removePiece(source);
        board.removePiece(target);
        board.placePiece(target, sourcePiece);

        /* If the moved piece is the king, it's necessary to update the king position. */
        kingPosition = sourcePiece instanceof King ? target : kingPosition;

        if (ChessMatch.kingCheck != validatePossibleCheck(kingPosition)) {
            ChessMatch.kingCheck = false;
        } else {
            JOptionPane.showMessageDialog(null, "King is in check.",
                    "Chess", JOptionPane.INFORMATION_MESSAGE, null);
            ChessMatch.kingCheck = true;
        }

        /* Undo the selected piece move. */
        board.removePiece(target);
        board.placePiece(source, sourcePiece);

        if (targetPiece != null)
            board.placePiece(target, targetPiece);
    }

    /*
     * Checks if the king in check has any legal move on the board, If the player can move no piece
     * then end the game with a checkmate.
     */
    public void isCheckmate() {
        King king = (King) board.getPieceOn(board.findKingOnBoard(getPlayerColor()));

        if (ChessMatch.kingCheck && !king.hasAnyValidMove() && !InputController.playerHasLegalMoves) {
            JOptionPane.showMessageDialog(null, "Checkmate. Game over.",
                    "Chess", JOptionPane.INFORMATION_MESSAGE, null);
            System.exit(0);
        }
    }

    /*
     * If the player in turn has no legal move, and the king isn't in check but has no legal move too,
     * end the game with a stalemate, and the game automatically is a draw.
     *
     * This method analyzes the pieces on the board to check if it is possible for each player to perform a checkmate.
     */
    public void isStalemate() {

        /*
         * King versus king;
         * King and bishop versus king;
         * King and knight versus king;
         * King and bishop versus king and bishop with the bishops on the same color.
         *
         * All these conditions lead to a game with impossible checkmate, leading to a draw.
         */
    }

    /* Place the piece's instances on the board. */
    private void loadInitialPieces() {
        board.placePiece(new Position(0, 7), new Rook(board, ChessColor.WHITE));
        board.placePiece(new Position(1, 7), new Knight(board, ChessColor.WHITE));
        board.placePiece(new Position(2, 7), new Bishop(board, ChessColor.WHITE));
        board.placePiece(new Position(3, 7), new King(board, ChessColor.WHITE, this));
        board.placePiece(new Position(4, 7), new Queen(board, ChessColor.WHITE));
        board.placePiece(new Position(5, 7), new Bishop(board, ChessColor.WHITE));
        board.placePiece(new Position(6, 7), new Knight(board, ChessColor.WHITE));
        board.placePiece(new Position(7, 7), new Rook(board, ChessColor.WHITE));

        for (int a = 0; a < Sizes.getBOARD_SIZE(); a++) {
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

        for (int a = 0; a < Sizes.getBOARD_SIZE(); a++) {
            board.placePiece(new Position(a, 1), new Pawn(board, ChessColor.BLACK, this));
        }
    }
}