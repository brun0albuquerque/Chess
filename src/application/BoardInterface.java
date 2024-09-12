package application;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardInterface extends JPanel {

    private final PieceDrawer pieces;
    private final Sizes sizes;
    private ChessMatch match;
    private ChessPiece piece;
    private Position source;
    private Position target;

    public BoardInterface(PieceDrawer pieces, ChessMatch match, Sizes sizes) {
        super();
        this.pieces = pieces;
        this.sizes = sizes;
        this.match = match;
        this.source = null;
        this.target = null;

        setPreferredSize(new Dimension(sizes.getDIMENSION(), sizes.getDIMENSION()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX() / sizes.getTILE_SIZE();
                int y = e.getY() / sizes.getTILE_SIZE();
                System.out.println("Clicked at: " + x + ", " + y);

                if (source != null && source.equals(target)) {
                    source = null;
                }

                if (source == null) {
                    source = new Position(x, y);
                } else {
                    try {
                        target = new Position(x, y);
                        piece = match.performChessMove(source, target);
                        pieces.movePiecesIcons(source, target);
                    } catch (InterfaceException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(),
                                "Invalid movement", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        source = null;
                        target = null;
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

        for (int row = 0; row < sizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < sizes.getBOARD_SIZE(); col++) {
                g.setColor(isWhite(row, col) ? InterfaceColor.LIGHT_BLUE : InterfaceColor.BLUE);
                g.fillRect(row * sizes.getTILE_SIZE(), col * sizes.getTILE_SIZE(),
                        sizes.getTILE_SIZE(), sizes.getTILE_SIZE());
            }
        }
        pieces.placePiecesOnBoard(g);
    }
}
