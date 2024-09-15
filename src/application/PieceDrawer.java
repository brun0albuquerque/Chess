package application;

import javax.swing.*;
import java.awt.*;

public class PieceDrawer extends JPanel {

    private final ImageIcon[][] piecesIcons;

    public PieceDrawer(ImageIcon[][] piecesIcons) {
        this.piecesIcons = piecesIcons;
    }

    public ImageIcon[][] getPiecesIcons() {
        return piecesIcons;
    }

    public ImageIcon getPieceIcon(int x, int y) {
        return piecesIcons[x][y];
    }

    public void placePieceIcon(int x, int y, ImageIcon image) {
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
        placePieceIcon(bX, bY, icon);
    }

    // Place all the pieces images on the board
    public void placePiecesOnBoard(Graphics g) {
        if (piecesIcons == null) {
            JOptionPane.showMessageDialog(null, "Game files could not be loaded.",
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }

        for (int row = 0; row < InterfaceSizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < InterfaceSizes.getBOARD_SIZE(); col++) {
                if (piecesIcons[row][col] != null) {
                    Image image = piecesIcons[row][col].getImage();
                    Image resizedImage = image.getScaledInstance(InterfaceSizes.getSmallPieceSize(),
                            InterfaceSizes.getSmallPieceSize(), Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(resizedImage);
                    newImage.paintIcon(this, g,
                            row * InterfaceSizes.getSmallTileSize(),
                            col * InterfaceSizes.getSmallTileSize());
                }
            }
        }
    }
}
