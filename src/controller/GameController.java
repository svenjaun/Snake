package controller;

import view.SnakeFrame;
import view.StartFrame;

public class GameController {

    StartFrame start;

    public GameController() {
        start = new StartFrame(this);
    }

    public void startGame() {
        start.dispose();
        new SnakeFrame();
    }
}
