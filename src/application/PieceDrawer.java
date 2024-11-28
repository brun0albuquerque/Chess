package application;

import chess.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

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

    /* Make the moves of the piece icons on the board. */
    public void iconMove(int aX, int aY, int bX, int bY) {
        ImageIcon icon = getPieceIcon(aX, aY);
        if (icon == null) {
            return;
        }
        removePieceIcon(aX, aY);
        placePieceIcon(bX, bY, icon);
    }

    /* Do the change of a pawn icon to a queen icon when the pawn is promoted. */
    public void graphicPawnPromotion(int aX, int aY, ChessColor color) {
        /* Checks the color of the piece because the icons files are different. */
        if (color == ChessColor.WHITE) {
            ImageIcon whiteQueen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/white_queen.png")));
            removePieceIcon(aX, aY);
            placePieceIcon(aX, aY, whiteQueen);
        } else {
            ImageIcon blackQueen = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/black_queen.png")));
            removePieceIcon(aX, aY);
            placePieceIcon(aX, aY, blackQueen);
        }
    }

    /* Load all pieces icons to the board. */
    public void placePiecesOnBoard(Graphics g) {
        /* If there is any problem with the pieces icons, then the game cannot be initiated, so it will close. */
        if (piecesIcons == null) {
            JOptionPane.showMessageDialog(null, "Game files could not be loaded.",
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }

        /* Resizes every icon to the size of the tile. */
        for (int row = 0; row < Sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < Sizes.getBOARD_SIZE(); col++) {
                if (piecesIcons[row][col] != null) {
                    Image image = piecesIcons[row][col].getImage();
                    Image resizedImage = image.getScaledInstance(Sizes.getSmallPieceSize() - 1,
                            Sizes.getSmallPieceSize() - 1, Image.SCALE_SMOOTH);
                    ImageIcon newImage = new ImageIcon(resizedImage);
                    newImage.paintIcon(this, g, row * Sizes.getSmallTileSize(), col * Sizes.getSmallTileSize());
                }
            }
        }
    }
}
