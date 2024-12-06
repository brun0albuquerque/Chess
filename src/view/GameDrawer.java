package view;

import boardgame.Position;
import chess.ChessColor;
import chess.ChessMatch;
import chess.KingNotFoundException;
import pieces.King;
import pieces.Rook;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class GameDrawer extends JPanel {

    private final ImageIcon[][] piecesIcons;
    private final ChessMatch match;

    public GameDrawer(ImageIcon[][] piecesIcons, ChessMatch match) {
        this.piecesIcons = piecesIcons;
        this.match = match;
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
    public void iconMove(Integer aX, Integer aY, Integer bX, Integer bY) {
        if (aX == null || aY == null || bX == null || bY == null) {
            throw new IllegalArgumentException("Coordinates cannot be null.");
        }

        ImageIcon icon = getPiecesIcons()[aX][aY];

        if (Objects.isNull(icon))
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

    /**
     * Perform the icon's move on the panel.
     * @param aX {@code x} coordinate from the first piece.
     * @param aY {@code y} coordinate from the first piece.
     * @param bX {@code x} coordinate from the second piece.
     * @param bY {@code y} coordinate from the second piece.
     * @throws KingNotFoundException if {@code King}'s instance is not found.
     */
    public void graphicPieceMove(Integer aX, Integer aY, Integer
            bX, Integer bY) throws KingNotFoundException {

        /* Get the rook's position before the move and calculate the rook's position
        after the move. */
        int kingRow = (aX > bX) ? aX - 2 : aX + 2;
        int rookRow = (aX > bX) ? bX + 2 : bX - 3;

        Optional<Position> optionalKingRow = Optional.of(new Position(kingRow, aY));
        Optional<Position> optionalRookRow = Optional.of(new Position(rookRow, bY));

        if (match.validateCastlingMove(optionalKingRow.get(),
                optionalRookRow.get())
                && match.validateCastlingPieces(optionalKingRow.get(),
                optionalRookRow.get())) {

            iconMove(aX, aY, optionalKingRow.get().getRow(), aY);
            iconMove(bX, bY, optionalRookRow.get().getRow(), bY);

            /*
             * Add the movement counter for both pieces only after the move,
             * because the validate method can only perform the castle move
             * with the movement counter equal to zero.
             * So add the movement counter after the graphical move to
             * make sure the piece icon also moves.
             */
            ChessColor playerColor = match.getPlayerColor();
            Optional<Position> optionalKingPosition = Optional
                    .ofNullable(match.getBoard().getKingPosition(playerColor));

            if (optionalKingPosition.isEmpty())
                throw new KingNotFoundException("King piece not found.");

            King king = (King) match.getBoard().getPiece(optionalKingPosition.get());
            Rook rook = (Rook) match.getBoard().getPiece(optionalRookRow.get());
            king.addMoveCount();
            rook.addMoveCount();
        } else {
            iconMove(aX, aY, bX, bY);
        }
    }

    /* Load all pieces icons to the board. */
    public void placePiecesOnBoard(Graphics g) {

        /* If there is any problem with the piece icons, then the game cannot
        be initiated, so it will close. */
        if (Objects.isNull(piecesIcons)) {
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

                if (Objects.nonNull(piecesIcons[row][col])) {
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
