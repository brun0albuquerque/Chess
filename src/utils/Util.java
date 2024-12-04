package utils;

import application.FrameSizes;
import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;

import java.util.ArrayList;

public class Util {

    private static ChessMatch match;

    public Util() {
    }

    /**
     * Calls the max and min methods from Math to always return an int bigger than 0 and smaller than the board size.
     * @param value the value to be checked.
     * @return an integer value bigger than 0 and smaller than the board size.
     */
    public static int valueWithinLimits(int value) {
        return Math.max(0, Math.min(FrameSizes.getBOARD_SIZE() - 1, value));
    }

    /**
     * Merge two matrices values.
     * This method is used to allow the king to move to safe squares, by setting false to all
     * positions an opponent piece can move to.
     * @param source is the matrix which will be analyzed if the given position in the loop is true.
     * @param result is the matrix that receives the value parameter if each position is true in source.
     * @param value is the boolean value that the result position will receive.
     * @return the result of the merge.
     */
    public static boolean[][] mergePossibilities(boolean[][] source, boolean[][] result, boolean value) {
        for (int a = 0; a < source.length; a++) {
            for (int b = 0; b < source.length; b++) {

                /* If a position at the source is true, the result is marked as false at the same position in result. */
                if (source[a][b])
                    result[a][b] = value;
            }
        }
        return result;
    }

    /**
     * Counts how many pieces are on the board.
     * @param board contains all the game pieces.
     * @return the number of pieces on the board.
     */
    public static int getNumberOfPieces(Board board) {
        Piece[][] pieces = board.getBoardPieces();
        int counter = 0;

        for (Piece[] arr : pieces) {
            for (Piece piece : arr) {
                if (piece != null)
                    counter++;
            }
        }
        return counter;
    }

    /**
     * Get all pieces on the board.
     * @param board contains all the game pieces.
     * @return a list of all the pieces on the board.
     */
    public static ArrayList<Piece> getPiecesList(Board board) {
        ArrayList<Piece> piecesList = new ArrayList<>();
        Piece[][] pieces = board.getBoardPieces();

        for (Piece[] row : pieces) {
            for (Piece piece : row) {
                if (piece != null) {
                    piecesList.add(piece);
                }
            }
        }
        return piecesList;
    }

    /**
     * Checks if a number is even.
     * @param x is the number to be checked.
     * @return true if x is even.
     */
    public static boolean isEven(int x) {
        return x % 2 == 0;
    }
}
