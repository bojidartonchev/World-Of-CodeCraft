package Main;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("World of CodeCraft");
        window.setIconImage(new ImageIcon("/Icons/WOCIcon.png").getImage());
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }
}
