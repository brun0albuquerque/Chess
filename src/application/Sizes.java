package application;

public enum Sizes {
    ;
    public static final int BOARD_SIZE = 8;
    public static final int TILE_SIZE = 76;
    public static final int PIECE_SIZE = 120;

    Sizes() {
    }

    public static int getBoardSize() {
        return BOARD_SIZE;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getPieceSize() {
        return PIECE_SIZE;
    }
}
