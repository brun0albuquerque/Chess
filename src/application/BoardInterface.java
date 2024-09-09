package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static application.Sizes.*;

public class BoardInterface extends JPanel {

    private final PieceDrawer pieces;

    public BoardInterface(PieceDrawer pieces) {
        super();
        this.pieces = pieces;
        setPreferredSize(new Dimension(720, 720));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX() / TILE_SIZE;
                int y = e.getY() / TILE_SIZE;
            }
        });
    }

    private boolean isWhite(int a, int b) {
        return (a + b) % 2 == 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                g.setColor(isWhite(row, col) ? BEIGE : BROWN);
                g.fillRect(row * ROW_SIZE, col * TILE_SIZE, ROW_SIZE, TILE_SIZE);
            }
        }
        pieces.placePiecesOnBoard(g);
    }
}
