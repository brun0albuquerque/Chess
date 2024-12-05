package controller;

import application.GameDrawer;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.KingNotFoundException;
import pieces.King;
import pieces.Rook;
import util.Util;

import java.util.Arrays;
import java.util.Optional;

public class GameController {

    private final ChessMatch match;
    private final GameDrawer drawer;

    protected static Integer aX = null;
    protected static Integer aY = null;
    protected static Integer bX = null;
    protected static Integer bY = null;

    protected static Position source = null;
    protected static Position target = null;

    public boolean playerHasLegalMoves;

    public GameController(ChessMatch match, GameDrawer drawer) {
        this.match = match;
        this.drawer = drawer;
    }

    /**
     * Handles the player interactions on the board.
     * @param x the row coordinate of the player interaction on the screen.
     * @param y the column coordinate of the player interaction on the screen.
     */
    protected void handleScreenSelection(int x, int y) {
        Position position = new Position(x, y);
        ChessPiece selectedPiece = (ChessPiece) match.getBoard().getPiece(position);

        if (isAllCoordinatesNull() && Util.isObjectNonNull(selectedPiece)
                && match.validatePieceColor(position)) {

            if (aX != null && aY != null && match.validateCastlingPieces(
                    new Position(aX, aY), position)) {
                bX = x;
                bY = y;
                target = new Position(bX, bY);
                return;
            }
            aX = x;
            aY = y;
            source = new Position(aX, aY);

        } else if (aX != null && aY != null) {
            bX = x;
            bY = y;
            target = new Position(bX, bY);
        }
    }

    /**
     * Checks if any coordinate is not null.
     * @return true if all coordinates are null.
     */
    protected boolean isAllCoordinatesNull() {
        return Util.isObjectNull(aX) || Util.isObjectNull(aY)
                || Util.isObjectNull(bX) || Util.isObjectNull(bY)
                || Util.isObjectNull(source) || Util.isObjectNull(target);
    }

    /**
     * Sets all coordinates null.
     */
    protected void cleanAllCoordinates() {
        source = null;
        target = null;
        aX = null;
        aY = null;
        bX = null;
        bY = null;
    }

    /**
     * This method validates if a move can be performed in the game.
     * @return true if a move can be performed, else false.
     */
    protected boolean verifyGameMove() {
        if (match.kingCheck) {
            cleanAllCoordinates();
            return false;
        }

        if (!match.validateSourcePosition(source) && !match.validateTargetPosition(target))
            return false;

        /* Validate if the move can be done checking the target position on the board.
         * If the piece is the king, it only allows the move to safe squares. */
        if (!match.validateMoveExecution(source, target)) {
            cleanAllCoordinates();
            return false;
        }
        return true;
    }

    /**
     * Performs all the movements in the game.
     * Both logical and visual moves.
     * Performs logical and visual moves, as well as handling all exceptions
     * thrown during moves.
     * @throws KingNotFoundException if any method tries to check the king's
     * position and can't find the king instance.
     */
    protected void controllerActions() throws KingNotFoundException {
        try {
            if (verifyGameMove())
                performChessMove();

            /* Get the rook's position before the move and calculate the rook's position
            after the move. */
            Optional<Integer> optionalKingRow = Optional.of(
                    (aX > bX) ? aX - 2 : aX + 2
            );

            Optional<Integer> optionalRookRow = Optional.of(
                    (aX > bX) ? bX + 2 : bX - 3
            );

            Optional<Position> optionalKingRowPosition = Optional.of(
                    new Position(optionalKingRow.get(), aY));

            Optional<Position> optionalRookRowPosition = Optional.of(
                    new Position(optionalRookRow.get(), bY));

            if (match.validateCastlingMove(optionalKingRowPosition.get(),
                    optionalRookRowPosition.get())
                    && match.validateCastlingPieces(optionalKingRowPosition.get(),
                    optionalRookRowPosition.get())) {

                drawer.executeIconMove(aX, aY, optionalKingRow.get(), aY);
                drawer.executeIconMove(bX, bY, optionalRookRow.get(), bY);

                /*
                 * Add the movement counter for both pieces only after the move,
                 * because the validate method can only perform the castle move
                 * with the movement counter equal to zero.
                 * So add the movement counter after the graphical move to
                 * make sure the piece icon also moves.
                 */
                ChessColor playerColor = match.getPlayerColor();
                Optional<Position> optionalKingPosition = Optional
                        .ofNullable(match.getBoard().getKingPosition(playerColor));

                if (optionalKingPosition.isEmpty())
                    throw new KingNotFoundException("King position is null");

                King king = (King) match.getBoard().getPiece(optionalKingPosition.get());
                Rook rook = (Rook) match.getBoard().getPiece(optionalRookRowPosition.get());
                king.addMoveCount();
                rook.addMoveCount();
            } else {
                drawer.executeIconMove(aX, aY, bX, bY);
            }

            ChessPiece piece = (ChessPiece) match.getBoard().getPiece(target);

            /* Checks for pawn promotion. */
            if (match.validatePawnPromotion(piece)) {
                match.performPawnPromotion(target, piece);
                drawer.graphicPawnPromotion(bX, bY, piece.getColor());
            }
        } catch (NullPointerException exception) {
            System.out.println(Arrays.toString(exception.getStackTrace()));

            /*
             * A null pointer exception can sometimes happen when the player
             * clicks on empty squares multiple times and then clicks on a piece.
             * When it happens, the coordinates will be cleaned, making them
             * null again, preventing the game crash.
             */
            cleanAllCoordinates();
        } finally {
            playerHasLegalMoves = playerHasAnyLegalMove();
            cleanAllCoordinates();
        }
    }

    /**
     * Perform one of the three possible moves: a normal move, a castling move
     * or en passant.
     */
    protected void performChessMove() {
        /* Validate the castling move before perform the move. */
        if (match.validateCastlingPieces(source, target)
                && match.validateCastlingMove(source, target)) {
            performCastlingMove(source, target);
            return;
        }

        /* If the movement isn't an especial move, perform a normal move. */
        match.performPieceMove(source, target);
    }

    /**
     * Perform the castling move.
     */
    private void performCastlingMove(Position kingPosition, Position rookPosition) {
        King king = (King) match.getBoard().getPiece(kingPosition);
        Rook rook = (Rook) match.getBoard().getPiece(rookPosition);

        match.getBoard().removePiece(kingPosition);
        match.getBoard().removePiece(rookPosition);

        /* When the king's position is higher than the rook's position (further away),
        perform the long castling, otherwise perform the short castling. */
        int kingRow = (kingPosition.getRow() > rookPosition.getRow())
                ? kingPosition.getRow() - 2 : kingPosition.getRow() + 2;

        int rookRow = (kingPosition.getRow() > rookPosition.getRow())
                ? rookPosition.getRow() + 2 : rookPosition.getRow() - 3;

        Position kingAfterCastling = new Position(kingRow, kingPosition.getColumn());
        Position rookAfterCastling = new Position(rookRow, rookPosition.getColumn());

        match.getBoard().placePiece(kingAfterCastling, king);
        match.getBoard().placePiece(rookAfterCastling, rook);
        match.nextTurn();
    }

    /**
     * Check if the player has any legal move to do in the game.
     * @return true if the player has any valid move to perform, else false.
     */
    public boolean playerHasAnyLegalMove() {
        Piece[][] boardPieces = match.getBoard().getBoardPieces();

        for (Piece[] boardRow : boardPieces) {
            for (Piece piece : boardRow) {

                if (Util.isObjectNonNull(piece)
                        && ((ChessPiece) piece)
                        .getColor()
                        .equals(match.getPlayerColor())) {

                    if (piece.hasAnyLegalMove())
                        return true;
                }
            }
        }
        return false;
    }
}