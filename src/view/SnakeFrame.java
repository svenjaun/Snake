package view;


import model.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Random;

public class SnakeFrame extends JFrame implements KeyListener, Runnable {

    Container content;

    private static int size = 41;

    JPanel[][] grid = new JPanel[size][size];

    Random rnd = new Random();

    Point fruit = new Point(1, 1);

    ArrayList<Point> snake = new ArrayList<>();

    Thread game = new Thread(this);

    boolean hasToResize = false;

    Direction currentDirection = Direction.EAST;

    int score = 0;


    public SnakeFrame() {
        super("Snake");
        content = getContentPane();
        content.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new JPanel();
                grid[i][j].setBackground(Color.BLACK);
                content.add(grid[i][j]);
            }
        }
        grid[size / 2][size / 2].setBackground(Color.green);
        snake.add(new Point(size / 2, size / 2));
        setSize(1000, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setFruit();
        game.start();
    }

    private void setFruit() {
        boolean isBumpingToSnake;
        int x;
        int y;
        do {
            isBumpingToSnake = false;
            x = rnd.nextInt(size - 3);
            y = rnd.nextInt(size - 3);

            for (int i = 0; i < snake.size(); i++) {
                if (x == snake.get(i).getX() && y == snake.get(i).getY()) {
                    isBumpingToSnake = true;
                }
            }


        } while (isBumpingToSnake);
        grid[x][y].setBackground(Color.WHITE);
        fruit.setLocation(x, y);
    }

    private void gameOver() {
        game.interrupt();
        JOptionPane.showMessageDialog(this, "Game Over\nYour Score is: " + score);
        System.exit(0);
    }

    @Override
    public void run() {
        while (true) {
            int x = 0;
            int y = 0;
            switch (currentDirection) {
                case EAST:
                    y += 1;
                    break;
                case WEST:
                    y -= 1;
                    break;
                case NORTH:
                    x -= 1;
                    break;
                case SOUTH:
                    x += 1;
                    break;
            }

            for (int i = 1; i < snake.size(); i++) {
                if (snake.get(0).getX() + x == snake.get(i).getX() && snake.get(0).getY() + y == snake.get(i).getY()) {
                    if (i == 2)
                        return;
                    else {
                        gameOver();
                    }
                }
            }


            if (!hasToResize) {
                grid[(int) (snake.get(snake.size() - 1).getX())][(int) (snake.get(snake.size() - 1).getY())].setBackground(Color.BLACK);
                moveSnake();
            } else {
                Point point = new Point((int)(snake.get(snake.size() - 1).getX()), (int) (snake.get(snake.size() - 1).getY()));
                moveSnake();
                snake.add(point);
                grid[(int) (snake.get(snake.size() - 1).getX())][(int) (snake.get(snake.size() - 1).getY())].setBackground(Color.green);
                hasToResize = false;
            }

            snake.set(0, new Point((int) (snake.get(0).getX() + x), (int) (snake.get(0).getY() + y)));

            if (snake.get(0).getX() < 0 || snake.get(0).getY() < 0 || snake.get(0).getX() > 40 || snake.get(0).getY() > 40) {
                gameOver();
                return;
            }

            grid[(int) (snake.get(0).getX())][(int) (snake.get(0).getY())].setBackground(Color.green);

            if (snake.get(0).getX() == fruit.getX() && snake.get(0).getY() == fruit.getY()) {
                score++;
                hasToResize = true;
                setFruit();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveSnake() {
        if (snake.size() != 1) {
            for (int i = snake.size() - 1; i > 0; i--) {
                snake.set(i, snake.get(i - 1));
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            currentDirection = Direction.EAST;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            currentDirection = Direction.WEST;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            currentDirection = Direction.SOUTH;
        else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
            currentDirection = Direction.NORTH;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
