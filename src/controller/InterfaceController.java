package controller;

import application.GameColors;
import application.GameDrawer;
import application.FrameSizes;
import boardgame.Piece;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import pieces.King;
import pieces.Pawn;
import pieces.Rook;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static controller.GameController.target;

public class InterfaceController extends JPanel {

    private final GameDrawer gameDrawer;
    private final ChessMatch match;

    public InterfaceController(GameController gameController, GameDrawer gameDrawer, ChessMatch match) {
        super();
        this.gameDrawer = gameDrawer;
        this.match = match;

        /* Set the game window size. */
        setPreferredSize(new Dimension(FrameSizes.getDimension(), FrameSizes.getDimension()));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                /* Get the coordinates of the click (in number of pixels). */
                int x = e.getX();
                int y = e.getY();

                /* Divide the coordinate by the size of the tile to get the square of the clicked area. */
                int row = x / FrameSizes.getTileSize();
                int col = y / FrameSizes.getTileSize();

                /* Make sure the row or column values are not less than 0 or greater than 7. */
                row = Utils.valueWithinLimits(row);
                col = Utils.valueWithinLimits(col);

                /* Set the values of the coordinates in MouseActions. */
                gameController.handlePieceSelection(row, col);

                /* When the player clicks on the board, repaint the board to highlight the moves. */
                if (GameController.aX != null && GameController.aY != null)
                    repaint();

                if (gameController.isAllCoordinatesNull())
                    return;

                try {
                    /*
                     * It performs normal movements and special movements, without the need to make a distinction in
                     * this Interface class.
                     * However, it is different for the graphical interface, since castling moves two pieces at the
                     * same time, to do the same for the piece icons, it is necessary to call the method more than once.
                     * I could create a method that performed a double movement of the icons, but this way the code is
                     * better and more readable.
                     */
                    if (gameController.validateLogicMove())
                        gameController.performChessMove();

                    int kingRow = (GameController.aX > GameController.bX) ? GameController.aX - 2 : GameController.aX + 2;
                    int rookRow = (GameController.aX > GameController.bX) ? GameController.bX + 2 : GameController.bX - 3;

                    Position kingRowPosition = new Position(kingRow, GameController.aY);
                    Position rookRowPosition = new Position(rookRow, GameController.bY);

                    if (match.validateCastlingMove(kingRowPosition, rookRowPosition)
                            && match.validateCastlingPieces(kingRowPosition, rookRowPosition)) {

                        gameDrawer.executeCastlingGraphicMove(
                                GameController.aX, GameController.aY, GameController.bX,
                                GameController.bY, kingRow, rookRow
                        );

                        /*
                         * Add the movement counter to one for both pieces after the move, because the validate
                         * method can only perform the castle move with the movement counter equal to zero.
                         * So add the movement counter after the graphical move to make sure the piece icon also moves.
                         */
                        King king = (King) match.getBoard().getPiece(match.getBoard().findKingOnBoard(match.getPlayerColor()));
                        Rook rook = (Rook) match.getBoard().getPiece(rookRowPosition);
                        king.addMoveCount();
                        rook.addMoveCount();
                    } else {
                        gameDrawer.executeIconMove(GameController.aX, GameController.aY, GameController.bX, GameController.bY);
                    }

                    ChessPiece piece = (ChessPiece) match.getBoard().getPiece(target);

                    /* Checks for pawn promotion. */
                    if (match.validatePawnPromotion(piece)) {
                        match.performPawnPromotion(target, piece);
                        gameDrawer.graphicPawnPromotion(GameController.bX, GameController.bY, piece.getColor());
                    }
                } catch (NullPointerException n) {

                    /*
                     * A null pointer exception can sometimes happen when the player clicks on empty squares
                     * multiple times and then clicks on a piece.
                     *
                     * When it happens, the coordinates will be cleaned, making them null again,
                     * preventing the game crash.
                     */
                    gameController.cleanAllCoordinates();
                } finally {

                    /*
                     * Always repaint the interface and clean the coordinates after a move to remove the
                     * selected piece highlights from the board and clean the coordinates.
                     */
                    repaint();
                    gameController.cleanAllCoordinates();
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

        /* The default background color of the panel is white, so I changed it to black because of the square edges.
        It looks better with a dark color. */
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        /* Divide the panel in a matrix of 8x8 squares, switching between two colors, representing the black and white
        from the classical chess board. The size of the tiles is calculated based on the frame (screen) size. */
        for (int row = 0; row < FrameSizes.getBOARD_SIZE(); row++) {
            for (int col = 0; col < FrameSizes.getBOARD_SIZE(); col++) {
                g.setColor(isWhite(row, col) ? GameColors.BLACK : GameColors.WHITE);

                /*
                 * This method will create the squares on the panel. The x and y values are for the left and right,
                 * the value I added is so the border can be seen. You add one more to the sides and one less to the
                 * width and height, so you have a slightly smaller tile and a "border" appearing around the tile.
                 * The size of the small tile had to be changed to be larger as the value added to x and y.
                 */
                g.fillRect(1 + col * FrameSizes.getTileSize(), 1 + row * FrameSizes.getTileSize(),
                        FrameSizes.getTileSize() - 1, FrameSizes.getTileSize() - 1);
            }
        }

        /* Get the position of the selected piece. */
        Integer selectedRow = GameController.aX;
        Integer selectedCol = GameController.aY;

        /* If there is a piece on the position, then it will highlight the piece possible movements. */
        if (selectedRow != null && selectedCol != null) {
            Position position = new Position(selectedRow, selectedCol);
            Piece piece = match.getBoard().getPiece(position);
            boolean[][] possibilities;

            /* If the selected piece is the king, then check the safe possible moves. If not, then only get the
            possible moves for the piece. */
            try {
                if (piece instanceof King)
                    possibilities = ((King) piece).possibleMoves();
                else
                    possibilities = piece.possibleMoves(true);
            } catch (NullPointerException e) {
                possibilities = new boolean[8][8];
            }

            /*
             * Since the chess board has a different system of coordinates from a computer matrix, the columns
             * need to be inverted, as with all the game coordinates that have the actual columns in the
             * row position and vice versa. This loop will paint the square another color to show the
             * player where the piece can move to.
             */
            for (int row = 0; row < FrameSizes.getBOARD_SIZE(); row++) {
                for (int col = FrameSizes.getBOARD_SIZE() - 1; col >= 0; col--) {
                    if (possibilities[col][row]) {
                        g.setColor(GameColors.HIGHLIGHTS);
                        g.fillRect(1 + col * FrameSizes.getTileSize(), 1 + row * FrameSizes.getTileSize(),
                                FrameSizes.getTileSize() - 1, FrameSizes.getTileSize() - 1);
                    }
                }
            }
        }
        gameDrawer.placePiecesOnBoard(g);
    }
}
