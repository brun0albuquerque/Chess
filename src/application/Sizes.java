package application;

public class Sizes {
    private final int BOARD_SIZE = 8;
    private int TILE_SIZE;
    private int PIECE_SIZE;
    private int DIMENSION;

    public Sizes() {
        setSmallSizes();
    }

    public int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public int getPIECE_SIZE() {
        return PIECE_SIZE;
    }

    public int getDIMENSION() {
        return DIMENSION;
    }

    public void setSmallSizes() {
        this.TILE_SIZE = 60;
        this.PIECE_SIZE = 60;
        this.DIMENSION = BOARD_SIZE * TILE_SIZE;
    }

    public void setMidSizes() {
        this.TILE_SIZE = 80;
        this.PIECE_SIZE = 80;
        this.DIMENSION = BOARD_SIZE * TILE_SIZE;
    }

    public void setBigSizes() {
        this.TILE_SIZE = 96;
        this.PIECE_SIZE = 96;
        this.DIMENSION = BOARD_SIZE * TILE_SIZE;
    }

    @Override
    public String toString() {
        return "Sizes{" +
                ", TILE_SIZE=" + TILE_SIZE +
                ", PIECE_SIZE=" + PIECE_SIZE +
                ", DIMENSION=" + DIMENSION +
                '}';
    }
}
