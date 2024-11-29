package controller;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.King;
import pieces.Pawn;
import pieces.Queen;

import javax.swing.*;


public class Movements {

    private final ChessMatch match;

    public Movements(ChessMatch match) {
        this.match = match;
    }

    /* Changes the position of a piece and make the capture of an opponent piece. */
    protected boolean pieceMove(Position source, Position target) {
        ChessPiece piece = (ChessPiece) match.getBoard().getPieceOn(source); /* Get the piece from the board. */

        /* Validate if the move can be done checking the target position on the board.
        If the piece is the king, only allows the move to safe squares. */
        if (!validateMovePosition(source, target)) return false;

        /* Only allows movement if the king is not in check with the piece's movement. */
        if (isKingInCheck(source, target)) return false;
        if (!isThereAnyPossibleMove()) System.exit(0);

        /* Remove both source and target pieces from the board. */
        match.getBoard().removePiece(source);
        match.getBoard().removePiece(target);

        /* Place the source piece on target position. */
        match.getBoard().placePiece(target, piece);

        /* Add the piece move counter by one. */
        piece.addMoveCount();

        /* Change to the next turn. */
        match.nextTurn();
        return true;
    }

    /*
     * Validates weather the selected position matches with any true position of possibilities. It will calculate
     * all possibilities for every piece on the board. If the piece it's the king, then only permit safe moves by
     * calling a method from King. Only returns true if the selected square has a true value in the matrix.
     */
    private boolean validateMovePosition(Position source, Position target) {
        boolean[][] possibilities;
        Piece piece = match.getBoard().getPieceOn(source);

        if (piece instanceof King) {
            possibilities = ((King) piece).possibleMoves();
        } else {
            possibilities = piece.possibleMoves(true);
        }
        return possibilities[target.getRow()][target.getColumn()];
    }

    /* Checks weather the pawn can be promoted or not, by checking its position. Pawns always be promoted to Queens. */
    public boolean checkPawnPromotion(Position position) {
        ChessPiece piece = (ChessPiece) match.getBoard().getPieceOn(position); /* Get the piece from the board. */

        if (piece instanceof Pawn && piece.getPosition().getColumn() == 0
                || piece instanceof Pawn && piece.getPosition().getColumn() == 7) {
            match.getBoard().removePiece(position);
            match.getBoard().placePiece(position, new Queen(match.getBoard(), piece.getColor()));
            return true;
        }
        return false;
    }

    /*   This method will simulate the movement and checks if the king's in check condition. */
    public boolean isKingInCheck(Position source, Position target) {
        Board board = match.getBoard();

        /* Get the selected piece to move and the captured piece. */
        Piece selectedPiece = board.getPieceOn(source);
        Piece capturedPiece = board.getPieceOn(target);

        /* Get the king position from the board. */
        Position kingPosition = match.findKingPosition(match.getPlayerColor());

        /* Throw an exception if the king isn't on the board (if the method returns null), which should be
        caught in Main. */
        if (kingPosition == null) throw new IllegalStateException();

        /* Move the piece to target position. */
        board.removePiece(source);
        board.removePiece(target);
        board.placePiece(target, selectedPiece);

        /* Iterates through the board to get the opponent piece's possible moves. */
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);

                /* If it's an opponent's piece, possibilities receive their moves. */
                if (match.validateOpponentPiecePosition(position)) {
                    boolean[][] possibilities = board.getPieceOn(position).possibleMoves(false);

                    /* If any piece movement matches the king's position, it returns true. */
                    if (possibilities[kingPosition.getRow()][kingPosition.getColumn()]) {

                        /* Undo the selected piece move. */
                        board.removePiece(target);
                        board.placePiece(source, selectedPiece);
                        board.placePiece(target, capturedPiece);

                        JOptionPane.showMessageDialog(null, "Can't leave the king in check.",
                                "King in check", JOptionPane.INFORMATION_MESSAGE, null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*   This method will simulate the movement and checks if the king's in check condition. */
    public boolean isThereAnyPossibleMove() {
        Board board = match.getBoard();

        /* Get the king position from the board. */
        Position kingPosition = match.findKingPosition(match.getPlayerColor());
        King king = (King) board.getPieceOn(kingPosition);

        /* Throw an exception if the king isn't on the board (if the method returns null), which should be
        caught in Main. */
        if (kingPosition == null) throw new IllegalStateException();

        /* Iterates through the board to get the opponent piece's possible moves. */
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);

                /* If it's an opponent's piece, possibilities receive their moves. */
                if (match.validateOpponentPiecePosition(position)) {
                    boolean[][] possibilities = board.getPieceOn(position).possibleMoves(false);

                    /* If any piece movement matches the king's position, it returns true. */
                    if (possibilities[kingPosition.getRow()][kingPosition.getColumn()]
                            && king.isThereAnyPossibleMove()) {

                        JOptionPane.showMessageDialog(null, "There is left move.",
                                "King in check", JOptionPane.INFORMATION_MESSAGE, null);
                        return true;
                    } else if (possibilities[kingPosition.getRow()][kingPosition.getColumn()]
                            && !king.isThereAnyPossibleMove()) {

                        JOptionPane.showMessageDialog(null, "Check mate. Game over.",
                                "Game over", JOptionPane.INFORMATION_MESSAGE, null);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* Checks if the king have any move left to do. */
    private boolean isKingWithoutMoves(boolean[][] aux, boolean[][] kingPossibilities, King king) {
        boolean[][] possibilities = king.mergePossibilities(aux, kingPossibilities, true);

        /* Iterate through all positions in the possibilities, looking for a true value, and when the given position
        is true, all possibilities will receive false at the same position. */
        for (int[] rows : king.getDirections()) {
            if (possibilities[rows[0]][rows[1]]) {
                kingPossibilities[rows[0]][rows[1]] = false;
            }
        }

        /* If there is no move left for the king, then returns false. */
        return king.isThereAnyPossibleMove();
    }
}
