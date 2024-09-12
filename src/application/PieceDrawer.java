package application;

import boardgame.Position;

import javax.swing.*;
import java.awt.*;

public class PieceDrawer extends Component {

    private final ImageIcon[][] piecesIcons;
    private final Sizes sizes;

    public PieceDrawer(ImageIcon[][] piecesIcons, Sizes sizes) {
        this.piecesIcons = piecesIcons;
        this.sizes = sizes;
    }

    public ImageIcon[][] getPiecesIcons() {
        return piecesIcons;
    }

    public ImageIcon getPieceIcon(int row, int col) {
        return piecesIcons[row][col];
    }

    public void setPieceIcon(int row, int col, ImageIcon image) {
        piecesIcons[row][col] = image;
    }

    public void removePieceIcon(int row, int col) {
        piecesIcons[row][col] = null;
    }

    // Make the move of the pieces icons on the board
    public void movePiecesIcons(Position source, Position target) {
        int sourceRow = source.getRow();
        int sourceCol = source.getColumn();
        int targetRow = target.getRow();
        int targetCol = target.getColumn();

        ImageIcon icon = getPieceIcon(sourceRow, sourceCol);
        removePieceIcon(sourceRow, sourceCol);
        setPieceIcon(targetRow, targetCol, icon);

        repaint();
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
                    newImage.paintIcon(this, g, col * sizes.getTILE_SIZE(), row * sizes.getTILE_SIZE());
                }
            }
        }
    }
}
