package controller;

import view.RestartFrame;
import view.SnakeFrame;
import view.StartFrame;

public class GameController {

    private StartFrame start;
    private SnakeController game;

    public GameController() {
        start = new StartFrame(this);
    }

    public void startGame() {
        start.dispose();
        game = new SnakeController(this);
    }

    void restartGame(int score) {
        game = null;
        new RestartFrame(this, score);
    }
}
