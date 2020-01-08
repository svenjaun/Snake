package controller;

import model.Direction;
import view.SnakeFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SnakeController implements Runnable {

    private Queue<Direction> directionQueue = new LinkedList<>();
    private Random rnd = new Random();
    private Point fruit = new Point(1, 1);
    private ArrayList<Point> snake = new ArrayList<>();

    private static int size = 41;
    private int score = 0;
    private SnakeFrame snakeFrame;
    private boolean hasToResize = false;
    private GameController gameController;
    private Thread game = new Thread(this);

    public SnakeController(GameController gameController) {
        this.gameController = gameController;
        startGame();
    }

    private void startGame() {
        snakeFrame = new SnakeFrame(this, size);
        snake.add(new Point(size / 2, size / 2));
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

            for (Point point : snake) {
                if (x == point.getX() && y == point.getY()) {
                    isBumpingToSnake = true;
                }
            }


        } while (isBumpingToSnake);
        snakeFrame.colorFruit(x,y);
        fruit.setLocation(x, y);
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
                snakeFrame.colorBackground((int) (snake.get(snake.size() - 1).getX()),(int) (snake.get(snake.size() - 1).getY()));
                moveSnake();
            } else {
                Point point = new Point((int) (snake.get(snake.size() - 1).getX()), (int) (snake.get(snake.size() - 1).getY()));
                moveSnake();
                snake.add(point);
                snakeFrame.colorSnake((int) (snake.get(snake.size() - 1).getX()), (int) (snake.get(snake.size() - 1).getY()));
                hasToResize = false;
            }

            snake.set(0, new Point((int) (snake.get(0).getX() + x), (int) (snake.get(0).getY() + y)));

            if (snake.get(0).getX() < 0 || snake.get(0).getY() < 0 || snake.get(0).getX() > 40 || snake.get(0).getY() > 40) {
                gameOver();
                return;
            }

             snakeFrame.colorSnake((int) (snake.get(0).getX()),(int) (snake.get(0).getY()));
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

    private void gameOver() {
        gameController.restartGame(score);
    }


    private void moveSnake() {
        if (snake.size() != 1) {
            for (int i = snake.size() - 1; i > 0; i--) {
                snake.set(i, snake.get(i - 1));
            }
        }
    }

    public int getQueueSize() {
        return directionQueue.size();
    }


    public void addToQueue(Direction direction) {
        directionQueue.add(direction);
    }
}
