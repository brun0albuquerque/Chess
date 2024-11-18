package controller;

import application.InterfaceColor;
import application.PieceDrawer;
import application.Sizes;
import boardgame.Position;
import chess.ChessMatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interface extends JPanel {

    private final MouseActions controller;
    private final PieceDrawer drawer;
    private final ChessMatch match;

    public Interface(MouseActions controller, PieceDrawer drawer, ChessMatch match) {
        super();
        this.controller = controller;
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

                controller.handlePieceSelection(row, col);
                movePiece(match);
            }
        });
    }

    private void movePiece(ChessMatch match) {
        if (!isCoordinatesNull()) {
            try {
                controller.logicMovement(match);
                drawer.graphicMovement(controller.getaX(), controller.getaY(), controller.getbX(), controller.getbY());
                repaint();
            } finally {
                controller.cleanPointers();
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

        for (int row = 0; row < Sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < Sizes.getBOARD_SIZE(); col++) {
                g.setColor(isWhite(row, col) ? InterfaceColor.LIGHT_BROWN : InterfaceColor.BROWN);
                g.fillRect(col * Sizes.getSmallTileSize(), row * Sizes.getSmallTileSize(),
                        Sizes.getSmallTileSize(), Sizes.getSmallTileSize());
            }
        }

        Integer selectedRow = controller.getaX();
        Integer selectedCol = controller.getaY();

        if (selectedRow != null && selectedCol != null) {
            Position position = new Position(selectedRow, selectedCol);
            boolean[][] selectedPiecePossibleMoves = match.getBoard().getPieceOnBoard(position).possibleMoves();

            for (int row = 0; row < Sizes.getBOARD_SIZE(); row++) {
                for (int col = 0; col < Sizes.getBOARD_SIZE(); col++) {
                    if (selectedPiecePossibleMoves[row][col]) {
                        System.out.println("Success!");
                        g.setColor(InterfaceColor.LIGHT_BLUE);
                        g.fillRect(col * Sizes.getSmallTileSize(), row * Sizes.getSmallTileSize(),
                                Sizes.getSmallTileSize(), Sizes.getSmallTileSize());
                    }
                }
            }
        }
        drawer.placePiecesOnBoard(g);
    }
}