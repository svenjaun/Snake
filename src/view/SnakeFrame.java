package view;


import controller.SnakeController;
import model.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class SnakeFrame extends JFrame implements KeyListener, Runnable {

    private SnakeController snakeController;
    private JPanel[][] grid;
    private int size;



    public SnakeFrame(SnakeController snakeController, int size) {
        super("Snake");
        this.size = size;
        this.snakeController = snakeController;
        grid = new JPanel[size][size];

        Container content = getContentPane();

        content.setLayout(new GridLayout(size, size));

        fillGrid(content);

        grid[size / 2][size / 2].setBackground(Color.green);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        content.setSize(screenSize.height / 9 * 8, screenSize.height / 9 * 8);
        setSize(screenSize.height / 9 * 8 + 10, screenSize.height / 9 * 8 + 10);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
    }

    private void fillGrid(Container content) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new JPanel();
                grid[i][j].setBackground(Color.BLACK);
                content.add(grid[i][j]);
            }
        }
    }



    @Override
    public void run() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (snakeController.getQueueSize() > 2) return;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            snakeController.addToQueue(Direction.EAST);
        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            snakeController.addToQueue(Direction.WEST);
        else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            snakeController.addToQueue(Direction.SOUTH);
        else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
            snakeController.addToQueue(Direction.NORTH);

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void colorFruit(int x, int y) {
        grid[x][y].setBackground(Color.WHITE);
    }

    public void colorSnake(int x, int y) {
        grid[x][y].setBackground(Color.GREEN);
    }

    public void colorBackground(int x, int y) {
        grid[x][y].setBackground(Color.BLACK);
    }
}
