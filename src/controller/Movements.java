package controller;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.King;
import pieces.Pawn;
import pieces.Queen;


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
//        if (isKingInCheck(source, target)) return false;

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

    /* Validates weather the selected position matches with any true position of possibilities. It will calculate
    all possibilities for every piece on the board. If the piece it's the king, then only permit safe moves by
    calling a method from King. Only returns true if the selected square has a true value in the matrix. */
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
        /* Get the selected piece to move. */
        Piece piece = match.getBoard().getPieceOn(source);

        /* Get a copy of the board. */
        Board board = piece.getBoard();

        /* Get the possible moves of the selected piece. */
        boolean[][] aux = piece.possibleMoves(true);

        /* Get the king position from the board. */
        Position kingPosition = findKingPosition(match.getPlayerColor());

        /* Throw an exception if the king isn't on the board (if the method returns null).
        Which should be caught in Main. */
        if (kingPosition == null) throw new IllegalStateException();

        /* Remove both source and target pieces from the board copy. */
        board.removePiece(source);
        board.placePiece(target, piece);

        return true;
    }

    /* Iterates through the board to find the king and return its position. */
    private Position findKingPosition(ChessColor color) {
        Board board = match.getBoard();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(row, col);
                Piece piece = board.getPieceOn(position);

                /* Only returns the king position if it's the same color as the player. */
                if (piece instanceof King && ((King) piece).getColor() == color) {
                    return position;
                }
            }
        }
        return null;
    }
}
