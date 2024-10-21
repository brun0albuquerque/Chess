package application;

import chess.ChessMatch;
import controller.BoardController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static controller.BoardController.cleanPointers;

public class BoardInterface extends JPanel {

    private final PieceDrawer drawer;
    private final BoardController controller;

    public BoardInterface(PieceDrawer drawer, ChessMatch match, BoardController controller) {
        super();
        this.drawer = drawer;
        this.controller = controller;

        setPreferredSize(new Dimension(InterfaceSizes.getSmallDimension(), InterfaceSizes.getSmallDimension()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int x = e.getX();
                int y = e.getY();

                int row = x / InterfaceSizes.getSmallTileSize();
                int col = y / InterfaceSizes.getSmallTileSize();

                row = 7 - Math.max(0, Math.min(7, row));
                col = 7 - Math.max(0, Math.min(7, col));

                controller.handlePieceSelection(row, col);
                movePiece(match);
            }
        });
    }

    private void movePiece(ChessMatch match) {
        if (!isCoordinatesNull()) {
            try {
                controller.movePiecesLogically(match);
                drawer.movePiecesIcons(7 - controller.getaX(), 7 - controller.getaY(),
                        7 - controller.getbX(), 7 - controller.getbY());
                repaint();
            } finally {
                cleanPointers();
            }
        }
    }

    private boolean isCoordinatesNull() {
        return controller.getaX() == null || controller.getaY() == null || controller.getbX() == null || controller.getbY() == null;
    }


    private boolean isWhite(int a, int b) {
        return (a + b) % 2 == 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < InterfaceSizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < InterfaceSizes.getBOARD_SIZE(); col++) {
                g.setColor(isWhite(row, col) ? InterfaceColor.LIGHT_BROWN : InterfaceColor.BROWN);
                g.fillRect(col * InterfaceSizes.getSmallTileSize(), row * InterfaceSizes.getSmallTileSize(),
                        InterfaceSizes.getSmallTileSize(), InterfaceSizes.getSmallTileSize());
            }
        }
        drawer.placePiecesOnBoard(g);
    }
}