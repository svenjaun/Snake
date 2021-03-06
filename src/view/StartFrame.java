package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartFrame extends JFrame implements KeyListener {
    private GameController gameController;

    public StartFrame(GameController gameController) {
        this.gameController = gameController;
        JLabel label = new JLabel("Controls: \n Arrow Keys or W, A, S, D");
        JButton button = new JButton("Start Game");
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_S)
            gameController.startGame();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
