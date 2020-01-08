package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RestartFrame extends JFrame implements KeyListener {

    private GameController gameController;

    public RestartFrame(GameController gameController, int score) {
        this.gameController = gameController;
        JLabel label = new JLabel("Game Over! \n Your score is: " + score);
        JButton button = new JButton("Restart Game");
        button.addActionListener(e -> gameController.startGame());
        setTitle("Start Game");
        button.addKeyListener(this);
        addKeyListener(this);
        setLocationRelativeTo(null);
        setSize(250, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        add(label, BorderLayout.NORTH);
        add(button, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_R)
            gameController.startGame();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
