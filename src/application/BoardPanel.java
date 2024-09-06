package application;

import boardgame.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static application.Sizes.BOARD_SIZE;
import static application.Sizes.TILE_SIZE;

public class BoardPanel extends JPanel {

    private final PiecesImages piecesImages;

    public BoardPanel(PiecesImages piecesImages) {
        super();
        this.piecesImages = piecesImages; // Load the pieces images files

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color brown = new Color(139, 69, 19, 220);
        Color beige = new Color(215, 195, 155);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boolean isWhite = (row + col) % 2 == 0;
                g.setColor(isWhite ? beige : brown);
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        placePiecesOnBoard(g);
    }

    protected void placePiecesOnBoard(Graphics g) {
        ImageIcon[][] pieces = piecesImages.getPieces();
        if (pieces != null) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (pieces[row][col] != null) {
                        Image image = pieces[row][col].getImage();
                        Image resizedImage = image.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
                        ImageIcon resizedIcon = new ImageIcon(resizedImage);


                        resizedIcon.paintIcon(this, g, col * TILE_SIZE, row * TILE_SIZE);
                    }
                }
            }
        }
    }

    private Position handleMouseClick(MouseEvent e) {
        int clickedColumn = e.getX() / TILE_SIZE;
        int clickedRow = e.getY() / TILE_SIZE;

        System.out.println("Col: " + clickedRow + "Row: " + clickedColumn);
        return new Position(clickedColumn, clickedRow);
    }
}
