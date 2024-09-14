package application;

import javax.swing.*;
import java.awt.*;

public class PieceDrawer extends JPanel {

    private ImageIcon[][] piecesIcons;
    private final Sizes sizes;

    public PieceDrawer(ImageIcon[][] piecesIcons, Sizes sizes) {
        this.piecesIcons = piecesIcons;
        this.sizes = sizes;
    }

    public ImageIcon[][] getPiecesIcons() {
        return piecesIcons;
    }

    public ImageIcon getPieceIcon(int x, int y) {
        return piecesIcons[x][y];
    }

    public void setPieceIcon(int x, int y, ImageIcon image) {
        piecesIcons[x][y] = image;
    }

    public void removePieceIcon(int x, int y) {
        piecesIcons[x][y] = null;
    }

    // Make the movements of the pieces icons on the board
    public void movePiecesIcons(int aX, int aY, int bX, int bY) {
        ImageIcon icon = getPieceIcon(aX, aY);
        if (icon == null) {
            return;
        }
        removePieceIcon(aX, aY);
        setPieceIcon(bX, bY, icon);
    }

    // Place all the pieces images on the board
    public void placePiecesOnBoard(Graphics g) {
        if (piecesIcons == null) {
            throw new NullPointerException("No images loaded.");
        }

        for (int row = 0; row < sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < sizes.getBOARD_SIZE(); col++) {
                if (piecesIcons[row][col] != null) {
                    Image image = piecesIcons[row][col].getImage();
                    Image resizedImage = image.getScaledInstance(sizes.getPIECE_SIZE(), sizes.getPIECE_SIZE(), Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(resizedImage);
                    newImage.paintIcon(this, g, row * sizes.getTILE_SIZE(), col * sizes.getTILE_SIZE());
                }
            }
        }
    }
}
