package chess;

import application.FrameSizes;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import controller.GameController;
import pieces.*;
import utils.Util;

import javax.swing.*;
import java.util.List;

public class ChessMatch {
    private int turn;
    private final Board board;
    private ChessColor playerColor;

    public static boolean kingCheck;
    public static boolean stalemate;
    public static boolean checkmate;

    public ChessMatch() {
        this.board = new Board(FrameSizes.getBOARD_SIZE(), FrameSizes.getBOARD_SIZE());
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

    /**
     * Changes the player turns.
     */
    public void nextTurn() {
        turn++;
        playerColor = invertColor(playerColor);
    }

    /**
     * Inverts the color of the player.
     * @param chessColor the player's color.
     */
    private ChessColor invertColor(ChessColor chessColor) {
        return chessColor == ChessColor.WHITE ? ChessColor.BLACK : ChessColor.WHITE;
    }

    /* Validation methods. Each method of validation will authenticate a condition from the game. */

    /**
     * Validate if there is a piece at the position and if it's the same color as the player.
     * @param position the position of the piece to be validated.
     * @return true if the position is not empty, else true.
     */
    public boolean validateSourcePosition(Position position) {
        return board.isPositionOccupied(position) && validatePieceColor(position);
    }

    /**
     * Return true if there is an opponent piece at the position, excluding any other case.
     * @param position the position of the piece to be validated.
     * @return true if the position is empty or the position has an opponent piece, else false.
     */
    public boolean validateTargetPosition(Position position) {
        if (board.isPositionOccupied(position) && validatePieceColor(position))
            return false;
        return !board.isPositionOccupied(position) || board.isPositionOccupied(position) && !validatePieceColor(position);
    }

    /**
     * It seems to be the same as validate target position, but it isn't.
     * Excludes any empty position and ally piece.
     * This method only returns true if there is a piece in a given position, and it is opponent's.
     * @param position the position of the piece to be validated.
     * @return true only if it has an opponent piece on the position.
     */
    public boolean validateOpponentPiecePosition(Position position) {
        if (!board.isPositionOccupied(position) || validateSourcePosition(position))
            return false;
        return board.isPositionOccupied(position) && !validatePieceColor(position);
    }

    /**
     * To validate the castle move, you need to be sure about the pieces.
     * This method makes sure the first piece is an instance of king and the second is an instance of rook and
     * if they haven't moved yet.
     * @param source the king's position.
     * @param target the rook's position.
     * @return true if the positions are occupied by an instance of the King and Rook classes and if
     * they have not yet moved.
     */
    public boolean validateCastlingPieces(Position source, Position target) {
        if (source != null && target != null)
            return false;

        if (board.isPositionOccupied(source) && board.isPositionOccupied(target))
            return false;

        if (board.getPiece(source) instanceof King && board.getPiece(target) instanceof Rook)
            return false;

        return (!((ChessPiece) board.getPiece(source)).pieceMoved() && !((ChessPiece) board.getPiece(target)).pieceMoved());
    }

    /**
     * Since the pawns don't start at the first row on their side of the board, when a pawn reaches the last
     * row, they can be promoted.
     * Only need to check if they are in the last row.
     * @param piece is the pawn which will be promoted.
     * @return true if the piece is an instance of Pawn class and if they have reached the las row of the board.
     */
    public boolean validatePawnPromotion(ChessPiece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("Piece is null.");
        }

        return piece instanceof Pawn && piece.getPosition().getColumn() == 0
                || piece instanceof Pawn && piece.getPosition().getColumn() == 7;
    }

    /**
     * Compare the color of the player and the piece.
     * @param position the position to be compared.
     * @return true if the piece's color is the same as the player.
     * */
    public boolean validatePieceColor(Position position) {
        return playerColor == ((ChessPiece) board.getPiece(position)).getColor();
    }

    /**
     * Checks if castling is possible. Checks if there are pieces between the king and the rook
     * and if the pieces have moved.
     * @param kingPosition the king's position.
     * @param rookPosition the rook's position.
     * @return true if the player can perform the castling move.
     * */
    public boolean validateCastlingMove(Position kingPosition, Position rookPosition) {
        /* Validate if one of king or rook positions is null to prevent null pointer exception. */
        if (kingPosition == null || rookPosition == null)
            return false;

        /* If king position has an instance of King, and if the rook position has an instance of Rook.
        It prevents a class cast exception. */
        if (!validateCastlingPieces(kingPosition, rookPosition))
            return false;

        King king = (King) board.getPiece(kingPosition);
        Rook rook = (Rook) board.getPiece(rookPosition);

        if (king == null || rook == null || king.pieceMoved() || rook.pieceMoved() && validatePieceColor(rook.getPosition()))
            return false;

        /* Get the rook position based on the king position to get the castling side. */
        int step = (rookPosition.getRow() > kingPosition.getRow()) ? 1 : -1;

        /* Check if there is another piece between the king and the rook. */
        for (int row = kingPosition.getRow() + step; row != rookPosition.getRow(); row += step) {
            if (board.getPiece(new Position(row, kingPosition.getColumn())) != null)
                return false;
        }
        return true;
    }

    /**
     * Validate each opponent's pieces until it find any possible move that threatens the king's position.
     * Return false no piece is threatening the king's position.
     * @param position the king's position.
     * @return true if the king is in check, else false.
     * */
    public boolean validatePossibleCheck(Position position) {
        Piece[][] boardPieces = board.getBoardPieces();
        Position opponentPosition;
        boolean[][] possibilities;

        for (Piece[] boardRow : boardPieces) {
            for (Piece piece : boardRow) {

                if (piece != null) {
                    opponentPosition = piece.getPosition();

                    /* If it's an opponent's piece, possibilities receive their possible moves. */
                    if (validateOpponentPiecePosition(opponentPosition)) {
                        possibilities = board.getPiece(opponentPosition).possibleMoves(false);

                        /* If any piece movement matches the king's position, it returns true. */
                        if (possibilities[position.getRow()][position.getColumn()])
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /* Movements and special moves methods. */

    /**
     * Perform a piece normal move on the board.
     * @param source the position of the piece that will execute the movement.
     * @param target the position which will the piece wll be moved.
     * */
    public void performPieceMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) getBoard().getPiece(source);

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

    /**
     * This method promotes a pawn to a queen.
     * Pawns will always be promoted to a new queen because it's the most valuable and powerful piece in the game.
     * @param position the pawn position.
     * @param piece the reference of the pawn.
     * */
    public void performPawnPromotion(Position position, ChessPiece piece) {
        board.removePiece(position);
        board.placePiece(position, new Queen(board, piece.getColor()));
    }

    public void enPassant() {

    }

    /**
     * This method will search and get the king's position on the board and verify if the king is in check.
     * If the king is in check and has any valid move, he will only allow the move that takes him out of check.
     * @param source the position of the first piece selected by the player.
     * @param target the position of the second piece selected by the player.
     */
    public void isKingInCheck(Position source, Position target) {
        Piece sourcePiece = board.getPiece(source);
        Piece targetPiece = board.getPiece(target);
        Position kingPosition = board.getKingPosition(getPlayerColor());

        if (kingPosition == null)
            throw new IllegalStateException();

        /* Check if the king is threatened before the move. */
        if (validatePossibleCheck(kingPosition))
            ChessMatch.kingCheck = true;
        else
            return;

        /* Move the piece to target position. */
        board.removePiece(source);
        board.removePiece(target);
        board.placePiece(target, sourcePiece);

        /* If the moved piece is the king, it's necessary to update the king position. */
        kingPosition = sourcePiece instanceof King ? target : kingPosition;

        if (ChessMatch.kingCheck && !validatePossibleCheck(kingPosition)) {
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

    /**
     * Checks if the king in check has any legal move on the board, If the player can move no piece
     * then end the game with a checkmate.
     * @return true if the king is in check and the player has no legal moves.
     */
    public boolean isCheckmate() {
        King king = (King) board.getPiece(board.getKingPosition(getPlayerColor()));

        if (ChessMatch.kingCheck && !king.hasAnyLegalMove() && !GameController.playerHasLegalMoves) {
            JOptionPane.showMessageDialog(null, "Checkmate. Game over.",
                    "Chess", JOptionPane.INFORMATION_MESSAGE, null);
            return true;
        }
        return false;
    }

    /**
     * This method analyzes the pieces on the board to check if it is possible for each player to perform a checkmate.
     * If the player in turn has no legal move, and the king isn't in check but has no legal move too,
     * end the game with a stalemate, and the game automatically is a draw.
     * @return true if the game can't finish because of not enough material to perform a checkmate.
     */
    public boolean isStalemate() {
        /*
         * All these conditions lead to a game with impossible checkmate, leading to a draw:
         * King versus king;
         * King and bishop versus king;
         * King and knight versus king;
         * King and bishop versus king and bishop with the bishops on the same color square.
         * Since it is not possible to capture anything in the first and second moves, at least 28 more turns
         * are needed, where captures will be made so that only 4 pieces remain on the board.
         */

        King king = (King) board.getPiece(board.getKingPosition(getPlayerColor()));

        /* Returns true if the king can't move and the player has no legal moves, but the king is not in check. */
        if (!ChessMatch.kingCheck && !king.hasAnyLegalMove() && !GameController.playerHasLegalMoves)
            return true;

        if (turn < 29)
            return false;

        int numberOfPiecesOnBoard = Util.getNumberOfPieces(board);

        if (numberOfPiecesOnBoard > 4)
            return false;

        List<Piece> activePieces = Util.getPiecesList(board);

        switch (activePieces.size()) {
            /* King versus king. */
            case 2:
                if (activePieces.stream().allMatch(piece -> piece instanceof King))
                    return true;
                break;

            /* King and bishop or knight versus king. */
            case 3:
                if (activePieces.stream().anyMatch(p -> p instanceof King) &&
                        activePieces.stream().anyMatch(p -> p instanceof Bishop || p instanceof Knight)) {
                    return true;
                }
                break;

            /* King and bishop versus king and bishop with the same color square. */
            case 4:
                long bishopCount = activePieces.stream().filter(piece -> piece instanceof Bishop).count();

                if (bishopCount == 2) {
                    Bishop[] bishops = activePieces.stream().filter(piece -> piece instanceof Bishop).toArray(Bishop[]::new);

                    if (!bishops[0].getColor().equals(bishops[1].getColor())
                            && bishops[0].squareColor() != bishops[1].squareColor()) {
                        return true;
                    }
                }
                break;

            default:
                return false;
        }
        return false;
    }

    /**
     * Instantiate the pieces on the board.
     * */
    private void loadInitialPieces() {
        board.placePiece(new Position(0, 7), new Rook(board, ChessColor.WHITE));
        board.placePiece(new Position(1, 7), new Knight(board, ChessColor.WHITE));
        board.placePiece(new Position(2, 7), new Bishop(board, ChessColor.WHITE));
        board.placePiece(new Position(3, 7), new King(board, ChessColor.WHITE, this));
        board.placePiece(new Position(4, 7), new Queen(board, ChessColor.WHITE));
        board.placePiece(new Position(5, 7), new Bishop(board, ChessColor.WHITE));
        board.placePiece(new Position(6, 7), new Knight(board, ChessColor.WHITE));
        board.placePiece(new Position(7, 7), new Rook(board, ChessColor.WHITE));

        for (int a = 0; a < FrameSizes.getBOARD_SIZE(); a++) {
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

        for (int a = 0; a < FrameSizes.getBOARD_SIZE(); a++) {
            board.placePiece(new Position(a, 1), new Pawn(board, ChessColor.BLACK, this));
        }
    }
}