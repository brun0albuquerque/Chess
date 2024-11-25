package controller;

import application.Colors;
import application.PieceDrawer;
import application.Sizes;
import boardgame.Position;
import chess.ChessMatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interface extends JPanel {

    private final PieceDrawer drawer;
    private final ChessMatch match;

    public Interface(MouseActions mouseActions, PieceDrawer drawer, ChessMatch match) {
        super();
        this.drawer = drawer;
        this.match = match;

        setPreferredSize(new Dimension(Sizes.getSmallDimension(), Sizes.getSmallDimension()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int x = e.getX();
                int y = e.getY();

                int row = x / Sizes.getSmallTileSize();
                int col = y / Sizes.getSmallTileSize();

                row = Math.max(0, Math.min(7, row));
                col = Math.max(0, Math.min(7, col));

                mouseActions.handlePieceSelection(row, col);

                if (MouseActions.aX != null && MouseActions.aY != null) {
                    repaint();
                }

                if (!mouseActions.isAllCoordinatesNull()) {
                    try {
                        mouseActions.logicMovement(match);
                        drawer.graphicMovement(MouseActions.aX, MouseActions.aY, MouseActions.bX, MouseActions.bY);
                    } finally {
                        repaint();
                        mouseActions.cleanAllCoordinates();
                    }
                }
            }
        });
    }

    private boolean isWhite(int a, int b) {
        return (a + b) % 2 == 0;
    }

    // This integrated method paints the board at the interface
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < Sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < Sizes.getBOARD_SIZE(); col++) {
                g.setColor(isWhite(row, col) ? Colors.LIGHT_BROWN : Colors.BROWN);
                g.fillRect(col * Sizes.getSmallTileSize(), row * Sizes.getSmallTileSize(),
                        Sizes.getSmallTileSize(), Sizes.getSmallTileSize());
            }
        }

        Integer selectedRow = MouseActions.aX;
        Integer selectedCol = MouseActions.aY;

        if (selectedRow != null && selectedCol != null) {
            Position position = new Position(selectedRow, selectedCol);
            boolean[][] selectedPiecePossibleMoves = match.getBoard().getPieceOn(position).possibleMoves();

            for (int row = 0; row <= 7; row++) {
                for (int col = 7; col >= 0; col--) {
                    if (selectedPiecePossibleMoves[col][7 - row]) {
                        g.setColor(Colors.LIGHT_BLUE);
                        g.fillRect(col * Sizes.getSmallTileSize(), row * Sizes.getSmallTileSize(),
                                Sizes.getSmallTileSize(), Sizes.getSmallTileSize());
                    }
                }
            }
        }
        drawer.placePiecesOnBoard(g);
    }
}