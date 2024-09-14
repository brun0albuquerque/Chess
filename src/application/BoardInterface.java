package application;

import chess.ChessMatch;
import chess.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardInterface extends JPanel {

    private final PieceDrawer drawer;
    private ChessMatch match;
    private ChessPiece piece;

    private Integer sourceX = null;
    private Integer sourceY = null;
    private Integer targetX = null;
    private Integer targetY = null;

    public BoardInterface(PieceDrawer drawer, ChessMatch match) {
        super();
        this.drawer = drawer;
        this.match = match;

        setPreferredSize(new Dimension(InterfaceSizes.getSmallDimension(), InterfaceSizes.getSmallDimension()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int x = e.getX();
                int y = e.getY();

                int col = x / InterfaceSizes.getSmallTileSize();
                int row = (y / InterfaceSizes.getSmallTileSize());

                col = Math.max(0, Math.min(7, col));
                row = Math.max(0, Math.min(7, row));

                handlePieceSelection(col, row);
            }
        });
    }

    private void handlePieceSelection(Integer col, Integer row) {
        try {
            if (sourceX == null && sourceY == null) {
                sourceX = col;
                sourceY = row;
            } else {
                targetX = col;
                targetY = row;

                // Only call the method if all coordinates are not null to prevent a null pointer exception
                if (sourceX != null && sourceY != null && targetX != null && targetY != null) {
                    drawer.movePiecesIcons(sourceX, sourceY, targetX, targetY);
                }
                sourceX = null;
                sourceY = null;
                repaint();
            }
        } catch (RuntimeException e) {
            throw new InterfaceException("No piece selected: " + e.getMessage());
        }
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