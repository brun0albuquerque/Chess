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

    private Integer aX = null;
    private Integer aY = null;
    private Integer bX = null;
    private Integer bY = null;

    public boolean playerHasLegalMoves;

    public GameController(ChessMatch match, GameDrawer drawer) {
        this.match = match;
        this.drawer = drawer;
    }

    public Integer getaX() {
        return aX;
    }

    public void setaX(Integer aX) {
        this.aX = aX;
    }

    public Integer getaY() {
        return aY;
    }

    public void setaY(Integer aY) {
        this.aY = aY;
    }

    public Integer getbX() {
        return bX;
    }

    public void setbX(Integer bX) {
        this.bX = bX;
    }

    public Integer getbY() {
        return bY;
    }

    public void setbY(Integer bY) {
        this.bY = bY;
    }

    /**
     * Handles the player interactions on the board.
     * @param x the row coordinate of the player interaction on the screen.
     * @param y the column coordinate of the player interaction on the screen.
     */
    protected void handleScreenSelection(int x, int y) {
        Position position = new Position(x, y);
        ChessPiece piece = (ChessPiece) match.getBoard().getPiece(position);

        if (isAllCoordinatesNull() && Util.isObjectNonNull(piece)
                && match.validatePieceColor(position)) {

            if (aX != null && aY != null && match.validateCastlingPieces(
                    new Position(aX, aY), position)) {
                setbX(x);
                setbY(y);
                return;
            }
            setaX(x);
            setaY(y);

        } else if (aX != null && aY != null) {
            setbX(x);
            setbY(y);
        }
    }

    /**
     * Checks if any coordinate is not null.
     * @return true if all coordinates are null.
     */
    protected boolean isAllCoordinatesNull() {
        return Util.isObjectNull(aX) || Util.isObjectNull(aY)
                || Util.isObjectNull(bX) || Util.isObjectNull(bY);
    }

    /**
     * Sets all coordinates null.
     */
    protected void cleanAllCoordinates() {
        setaX(null);
        setaY(null);
        setbX(null);
        setbY(null);
    }

    /**
     * This method validates if a move can be performed in the game.
     * @return true if a move can be performed, else false.
     */
    protected boolean verifyGameMove() {
        Optional<Position> optionalSource = Optional.of(new Position(aX, aY));
        Optional<Position> optionalTarget = Optional.of(new Position(bX, bY));

        if (match.kingCheck) {
            cleanAllCoordinates();
            return false;
        }

        if (!match.validateSourcePosition(optionalSource.get())
                && !match.validateTargetPosition(optionalTarget.get())) {
            return false;
        }

        /* Validate if the move can be done checking the target position on the board.
         * If the piece is the king, it only allows the move to safe squares. */
        if (!match.validateMoveExecution(optionalSource.get(), optionalTarget.get())) {
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

            Optional<Integer> optionalAX = Optional.of(aX);
            Optional<Integer> optionalAY = Optional.of(aY);
            Optional<Integer> optionalBX = Optional.of(bY);
            Optional<Integer> optionalBY = Optional.of(bY);
            Optional<Position> optionalTarget = Optional.of(new Position(bX, bY));

            System.out.println("Optional: " + optionalAX + ", " + optionalAY + ", " + optionalBX + ", " + optionalBY);

            /* Get the rook's position before the move and calculate the rook's position
            after the move. */
            int kingRow = (optionalAX.get() > optionalBX.get()) ? optionalAX.get() - 2 : optionalAX.get() + 2;
            int rookRow = (optionalAX.get() > optionalBX.get()) ? optionalBX.get() + 2 : optionalBX.get() - 3;

            Optional<Position> optionalKingRowPosition = Optional.of(
                    new Position(kingRow, optionalAY.get()));

            Optional<Position> optionalRookRowPosition = Optional.of(
                    new Position(rookRow, optionalBY.get()));

            if (match.validateCastlingMove(optionalKingRowPosition.get(),
                    optionalRookRowPosition.get())
                    && match.validateCastlingPieces(optionalKingRowPosition.get(),
                    optionalRookRowPosition.get())) {

                drawer.executeIconMove(
                        optionalAX.get(),
                        optionalAY.get(),
                        kingRow,
                        optionalAY.get()
                );

                drawer.executeIconMove(
                        optionalBX.get(),
                        optionalBY.get(),
                        rookRow,
                        optionalBY.get()
                );

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
                    throw new KingNotFoundException("King piece not found.");

                King king = (King) match.getBoard().getPiece(optionalKingPosition.get());
                Rook rook = (Rook) match.getBoard().getPiece(optionalRookRowPosition.get());
                king.addMoveCount();
                rook.addMoveCount();
            } else {
                drawer.executeIconMove(
                        optionalAX.get(),
                        optionalAY.get(),
                        optionalBX.get(),
                        optionalBY.get()
                );
            }

            ChessPiece piece = (ChessPiece) match.getBoard().getPiece(optionalTarget.get());

            /* Checks for pawn promotion. */
            if (match.validatePawnPromotion(piece)) {
                match.performPawnPromotion(optionalTarget.get(), piece);
                drawer.graphicPawnPromotion(optionalBX.get(), optionalBY.get(), piece.getColor());
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
        Optional<Position> optionalSource = Optional.of(new Position(aX, aY));
        Optional<Position> optionalTarget = Optional.of(new Position(bX, bY));

        /* Validate the castling move before perform the move. */
        if (match.validateCastlingPieces(optionalSource.get(), optionalTarget.get())
                && match.validateCastlingMove(optionalSource.get(), optionalTarget.get())) {

            performCastlingMove(optionalSource.get(), optionalTarget.get());
            return;
        }

        /* If the movement isn't an especial move, perform a normal move. */
        match.performPieceMove(optionalSource.get(), optionalTarget.get());
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