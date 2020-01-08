package view;


import controller.GameController;
import model.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SnakeFrame extends JFrame implements KeyListener, Runnable {

    private static int size = 41;
    private JPanel[][] grid = new JPanel[size][size];
    private Random rnd = new Random();
    private Point fruit = new Point(1, 1);
    private ArrayList<Point> snake = new ArrayList<>();
    private boolean hasToResize = false;
    private Queue<Direction> directionQueue = new LinkedList<>();
    private GameController gameController;

    private int score = 0;


    public SnakeFrame(GameController gameController) {
        super("Snake");
        this.gameController = gameController;
        Container content = getContentPane();
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(screenSize.height / 8 * 7, screenSize.height / 5 * 4);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        setFruit();
        Thread game = new Thread(this);
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

            for (Point point : snake) {
                if (x == point.getX() && y == point.getY()) {
                    isBumpingToSnake = true;
                }
            }


        } while (isBumpingToSnake);
        grid[x][y].setBackground(Color.WHITE);
        fruit.setLocation(x, y);
    }

    private void gameOver() {
        gameController.restartGame(score);
    }

    @Override
    public void run() {
        while (true) {
            int x = 0;
            int y = 0;

            if (directionQueue.size() == 2) {
                Direction[] a = directionQueue.toArray(new Direction[2]);
                if (a[0].equals(a[1])) {
                    directionQueue.remove();
                }
            }

            Direction removedDirection = (directionQueue.size() != 0) ? directionQueue.remove() : Direction.EAST;
            switch (removedDirection) {
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
            if (directionQueue.size() == 0) {
                directionQueue.add(removedDirection);
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
                Point point = new Point((int) (snake.get(snake.size() - 1).getX()), (int) (snake.get(snake.size() - 1).getY()));
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
        if (directionQueue.size() > 2) return;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            directionQueue.add(Direction.EAST);
        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            directionQueue.add(Direction.WEST);
        else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            directionQueue.add(Direction.SOUTH);
        else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
            directionQueue.add(Direction.NORTH);

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
