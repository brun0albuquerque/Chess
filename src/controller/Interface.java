package controller;

import application.Colors;
import application.PieceDrawer;
import application.Sizes;
import chess.ChessMatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interface extends JPanel {

    private final PieceDrawer drawer;

    public Interface(MouseActions mouseActions, PieceDrawer drawer, ChessMatch match) {
        super();
        this.drawer = drawer;

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < Sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < Sizes.getBOARD_SIZE(); col++) {
                g.setColor(isWhite(row, col) ? Colors.LIGHT_BLUE : Colors.BLUE);
                g.fillRect(col * Sizes.getSmallTileSize(), row * Sizes.getSmallTileSize(),
                        Sizes.getSmallTileSize(), Sizes.getSmallTileSize());
            }
        }
        drawer.placePiecesOnBoard(g);
    }
}