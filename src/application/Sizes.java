package application;

import java.awt.*;

public interface Sizes {
    int BOARD_SIZE = 8;
    int PIECE_SIZE = 64;

    int TILE_SIZE = 80;
    int ROW_SIZE = (int)(1.05 * TILE_SIZE);
    int MID_TILE_SIZE = 96;
    int BIG_TILE_SIZE = 135;

    int INIT_DIMENSION = 670;
    int MID_DIMENSION = 768;
    int BIG_DIMENSION = 1080;

    Color BROWN = new Color(139, 69, 19, 220);
    Color BEIGE = new Color(215, 195, 155);
}
