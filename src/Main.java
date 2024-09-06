import application.UserInterface;

public class Main {
    public static void main(String[] args) {

        System.out.println("Loading: " + Main.class.getResource("resources/chess_pieces/white_pawn.png"));

        UserInterface userInterface = new UserInterface(720, 720);
    }
}