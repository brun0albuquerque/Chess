package application;

import boardgame.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static application.Sizes.BOARD_SIZE;
import static application.Sizes.TILE_SIZE;

public class BoardPanel extends JPanel {

    private int xCoordinate, yCoordinate;

    private final PrintPieces printPieces;

    public BoardPanel(PrintPieces printPieces) {
        super();
        this.printPieces = printPieces; // Load the pieces images files


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int xCoordinate = e.getX() / TILE_SIZE;
                int yCoordinate = e.getY() / TILE_SIZE;

                if (!(xCoordinate >= 0 && xCoordinate < 8 && yCoordinate >= 0 && yCoordinate < 8)) {
                    throw new InterfaceException("Click outside the board is not valid.");
                }
            }
        });

    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public Position getClickCoordinates() {
        return new Position(getXCoordinate(), getYCoordinate());
    }

    protected void placePiecesOnBoard(Graphics g) {
        ImageIcon[][] piecesImages = printPieces.getPiecesImages();
        if (piecesImages != null) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (piecesImages[row][col] != null) {
                        Image image = piecesImages[row][col].getImage();
                        Image resizedImage = image.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
                        ImageIcon resizedIcon = new ImageIcon(resizedImage);

                        resizedIcon.paintIcon(this, g, col * TILE_SIZE, row * TILE_SIZE);
                    }
                }
            }
        }
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
}
