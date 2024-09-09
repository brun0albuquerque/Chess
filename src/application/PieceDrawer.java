package application;

import javax.swing.*;
import java.awt.*;

import static application.Sizes.*;

public class PieceDrawer extends Component {

    private final ImageIcon[][] piecesImages;

    public PieceDrawer(ImageIcon[][] piecesImages) {
        this.piecesImages = piecesImages;
    }

    public ImageIcon[][] getPiecesImages() {
        return piecesImages;
    }

    public void placePiecesOnBoard(Graphics g) {
        if (piecesImages == null) {
            throw new InterfaceException("No piecesImages loaded.");
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (piecesImages[row][col] != null) {
                    Image image = piecesImages[row][col].getImage();
                    Image resizedImage = image.getScaledInstance(PIECE_SIZE, PIECE_SIZE, Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(resizedImage);
                    newImage.paintIcon(this, g, col * TILE_SIZE, row * TILE_SIZE);
                }
            }
        }
    }
}
