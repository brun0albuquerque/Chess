package view;

public enum Sizes {
    SIZE(66, 66);

    private static final int BOARD_SIZE = 8;

    private final int tileSize;
    private final int pieceSize;

    Sizes(int tileSize, int pieceSize) {
        this.tileSize = tileSize;
        this.pieceSize = pieceSize;
    }

    public static int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public static int getTileSize() {
        return SIZE.tileSize;
    }

    public static int getPieceSize() {
        return SIZE.pieceSize;
    }

    public static int getDimension() {
        return BOARD_SIZE * SIZE.tileSize + 1;
    }
}
