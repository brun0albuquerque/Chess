package application;

public class Sizes {
    private final Integer BOARD_SIZE = 8;
    private Integer TILE_SIZE = null;
    private Integer PIECE_SIZE = null;
    private Integer DIMENSION = null;

    public Sizes() {
        setSmallSizes();
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

    public Integer getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    public Integer getTILE_SIZE() {
        return TILE_SIZE;
    }

    public Integer getPIECE_SIZE() {
        return PIECE_SIZE;
    }

    public Integer getDIMENSION() {
        return DIMENSION;
    }
}
