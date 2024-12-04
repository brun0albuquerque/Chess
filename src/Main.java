import application.GameInitializer;

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

            GameInitializer anInterface = new GameInitializer();
        } catch (IllegalArgumentException | IllegalStateException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()),
                    "Error", JOptionPane.ERROR_MESSAGE, null);
            System.exit(1);
        }
    }
}
