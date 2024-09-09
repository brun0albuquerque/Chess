package application;

import javax.swing.*;
import java.awt.*;

import static application.Sizes.*;

public class PieceDrawer extends Component {

    private final ImageIcon[][] piecesImages;
    private final Sizes sizes;

    public PieceDrawer(ImageIcon[][] piecesImages, Sizes sizes) {
        this.piecesImages = piecesImages;
        this.sizes = sizes;
    }

    public ImageIcon[][] getPiecesImages() {
        return piecesImages;
    }

    public void placePiecesOnBoard(Graphics g) {
        if (piecesImages == null) {
            throw new InterfaceException("No piecesImages loaded.");
        }

        for (int row = 0; row < sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < sizes.getBOARD_SIZE(); col++) {
                if (piecesImages[row][col] != null) {
                    Image image = piecesImages[row][col].getImage();
                    Image resizedImage = image.getScaledInstance(sizes.getPIECE_SIZE(), sizes.getPIECE_SIZE(), Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(resizedImage);
                    newImage.paintIcon(this, g, col * sizes.getTILE_SIZE(), row * sizes.getTILE_SIZE());
                }
            }
        }
    }
}
