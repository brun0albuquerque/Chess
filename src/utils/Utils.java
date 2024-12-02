package utils;

import application.FrameSizes;

public class Utils {

    public Utils() {
    }

    /**
     * Calls the max and min methods from Math to always return an int bigger than 0 and smaller than the board size.
     */
    public static int valueWithinLimits(int value) {
        return Math.max(0, Math.min(FrameSizes.getBOARD_SIZE() - 1, value));
    }

    /**
     * Merge two matrices values.
     * This method is used to allow the king to move to safe squares, by setting false to all
     * positions an opponent piece can move to.
     * @param source is the matrix which will be analyzed if the given position in the loop is true.
     * @param result is a matrix which will receive false if the loop position if the same position in source is true.
     * @param value is the value result will receive.
     * @return result after iterating through source and finish the merge.
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
}
