package application;

public enum Sizes {
    SMALL(60, 60),
    MID(80, 80),
    BIG(96, 96);

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

    public static int getSmallTileSize() {
        return SMALL.tileSize;
    }

    public static int getMidTileSize() {
        return MID.tileSize;
    }

    public static int getBigTileSize() {
        return BIG.tileSize;
    }

    public static int getSmallPieceSize() {
        return SMALL.pieceSize;
    }

    public static int getMidPieceSize() {
        return MID.pieceSize;
    }

    public static int getBigPieceSize() {
        return BIG.pieceSize;
    }

    public static int getSmallDimension() {
        return BOARD_SIZE * SMALL.tileSize + 1;
    }

    public static int getMidDimension() {
        return BOARD_SIZE * MID.tileSize;
    }

    public static int getBigDimension() {
        return BOARD_SIZE * BIG.tileSize;
    }
}
