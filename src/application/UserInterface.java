package application;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class UserInterface extends JFrame {

    private Dimension resolution;

    public UserInterface(int width, int height) {
        super();
        setTitle("Chess");
        setResolution(width, height);
        setSize(getResolution());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Media source (board logo): https://www.flaticon.com/free-icon/chess-board_107617
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/board.png")));
        setIconImage(imageIcon.getImage());

        setVisible(true);
    }

    public Dimension getResolution() {
        return resolution;
    }

    // Convert the resolution from type Integer to Dimension
    public void setResolution(int width, int height) {
        this.resolution = new Dimension(width, height);
    }
}
