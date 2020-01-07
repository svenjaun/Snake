package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {
    public StartFrame(GameController gameController) {
        JLabel label = new JLabel("Controls: \n Arrow Keys or W, A, S, D");
        JButton button = new JButton("Start Game");
        button.addActionListener(e -> gameController.startGame());
        setTitle("Start Game");
        setLocationRelativeTo(null);
        setSize(250, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        add(label, BorderLayout.NORTH);
        add(button, BorderLayout.CENTER);

        setVisible(true);
    }

}
