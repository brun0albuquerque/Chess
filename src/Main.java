import application.BoardPanel;
import application.UserInterface;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();
        UserInterface userInterface = new UserInterface(640, 640);
        BoardPanel boardPanel = userInterface.getBoardPanel();

        while (true) { // Each time the loop run, a new turn start
            try {
                System.out.println("Before exception.");
                Position source = boardPanel.getClickCoordinates(); // Read the source click
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                System.out.println("After exception.");

                userInterface.printBoard(chessMatch.getPieces(), possibleMoves);

                Position target = boardPanel.getClickCoordinates(); // Read the target click
                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

//                if (capturedPiece != null) {
//                    captured.add(capturedPiece);
//                }

//                if (chessMatch.getPromoted() != null) {
//                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
//                    String type = sc.nextLine().toUpperCase();
//                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
//                        System.out.print("Invalid value! Enter piece for promotion (B/N/R/Q): ");
//                        type = sc.nextLine().toUpperCase();
//                    }
//                    chessMatch.replacePromotedPiece(type);
//                }
            } catch (NullPointerException e) {
                throw new NullPointerException("Null pointer exception: " + e.getMessage());
            }
        }


    }
}