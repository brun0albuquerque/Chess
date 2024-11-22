package controller;

import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Moviments {

    private final ChessMatch match;
    private List<Piece> boardPieces;
    private List<Piece> capturedPieces;

    public Moviments(ChessMatch match) {
        this.match = match;
        this.boardPieces = match.getBoardPieces();
        this.capturedPieces = new ArrayList<>();
    }

    // Make the action of move a piece from a position to another (change the positions in board)
    private Piece movingPiece(Position source, Position target) {
        ChessPiece sourcePiece = (ChessPiece) match.getBoard().removePiece(source);
        sourcePiece.setMoveCount(sourcePiece.getMoveCount() + 1);
        Piece capturedPiece = match.getBoard().removePiece(target);

        if (capturedPiece != null) {
            boardPieces.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        match.getBoard().placePiece(target, sourcePiece);
        return capturedPiece;
    }

    // Change the position of a piece and make the capture of an opponent piece
    protected void chessPieceMovement(Position source, Position target) {
        Piece piece = movingPiece(source, target);

        if (piece != null) {
            ChessPiece chessPiece = (ChessPiece) piece;
            chessPiece.setMoveCount(chessPiece.getMoveCount() + 1);
        }
        match.nextTurn();
        System.out.println("Next turn.");
    }
}
