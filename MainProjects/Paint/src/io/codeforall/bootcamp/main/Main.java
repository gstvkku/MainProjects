package io.codeforall.bootcamp.main;

import io.codeforall.bootcamp.main.Game;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Game game = new Game();
        game.initGame();
    }
}
