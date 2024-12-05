package application;

import chess.ChessColor;
import util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameDrawer extends JPanel {

    private final ImageIcon[][] piecesIcons;

    public GameDrawer(ImageIcon[][] piecesIcons) {
        this.piecesIcons = piecesIcons;
    }

    public ImageIcon[][] getPiecesIcons() {
        return piecesIcons;
    }

    public void placePieceIcon(int x, int y, ImageIcon image) {
        piecesIcons[x][y] = image;
    }

    public void removePieceIcon(int x, int y) {
        piecesIcons[x][y] = null;
    }

    /* Perform the moves of the piece icons on the board. */
    public void executeIconMove(Integer aX, Integer aY, Integer bX, Integer bY) {
        if (aX == null || aY == null || bX == null || bY == null) {
            throw new IllegalArgumentException("Coordinates cannot be null.");
        }

        ImageIcon icon = getPiecesIcons()[aX][aY];

        if (Util.isObjectNull(icon))
            return;

        removePieceIcon(aX, aY);
        placePieceIcon(bX, bY, icon);
    }

    /* Do the change of a pawn icon to a queen icon when the pawn is promoted. */
    public void graphicPawnPromotion(int aX, int aY, ChessColor color) {

        /* Checks the color of the piece because the icon files are different. */
        if (color == ChessColor.WHITE) {
            ImageIcon whiteQueen = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/resources/white_queen.png"))
            );
            removePieceIcon(aX, aY);
            placePieceIcon(aX, aY, whiteQueen);
        } else {
            ImageIcon blackQueen = new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/resources/black_queen.png"))
            );
            removePieceIcon(aX, aY);
            placePieceIcon(aX, aY, blackQueen);
        }
    }

    /* Load all pieces icons to the board. */
    public void placePiecesOnBoard(Graphics g) {

        /* If there is any problem with the piece icons, then the game cannot
        be initiated, so it will close. */
        if (Util.isObjectNull(piecesIcons)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Game resources could not be loaded.",
                    "Error", JOptionPane.ERROR_MESSAGE,
                    null);
            System.exit(1);
        }

        /* Resizes every icon to the size of the tile. */
        for (int row = 0; row < Sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < Sizes.getBOARD_SIZE(); col++) {

                if (Util.isObjectNonNull(piecesIcons[row][col])) {
                    Image image = piecesIcons[row][col].getImage();

                    Image resizedImage = image.getScaledInstance(
                            Sizes.getPieceSize() - 1,
                            Sizes.getPieceSize() - 1,
                            Image.SCALE_SMOOTH);

                    ImageIcon newImage = new ImageIcon(resizedImage);
                    newImage.paintIcon(this, g,
                            row * Sizes.getTileSize(),
                            col * Sizes.getTileSize());
                }
            }
        }
    }
}
