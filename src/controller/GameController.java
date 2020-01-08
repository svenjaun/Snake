package controller;

import view.RestartFrame;
import view.SnakeFrame;
import view.StartFrame;

public class GameController {

    private StartFrame start;
    private SnakeFrame snake;

    public GameController() {
        start = new StartFrame(this);
    }

    public void startGame() {
        start.dispose();
        snake = new SnakeFrame(this);
    }

    public void restartGame(int score) {
        snake = null;
        new RestartFrame(this, score);
    }
}
