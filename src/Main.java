import application.Initializer;
import chess.ChessMatch;
import controller.InputController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            if (GraphicsEnvironment.isHeadless()) {
                JOptionPane.showMessageDialog(null, "This environment doesn't support graphics interface.",
                        "Error", JOptionPane.ERROR_MESSAGE, null);
                System.exit(1);
            }

            ChessMatch match = new ChessMatch();
            InputController action = new InputController(match);
            Initializer anInterface = new Initializer(action, match);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.out.printf(Arrays.toString(e.getStackTrace()));
            System.exit(1);
        } catch (IllegalArgumentException | IllegalStateException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }
    }
}
