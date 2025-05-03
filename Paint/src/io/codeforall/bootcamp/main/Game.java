package io.codeforall.bootcamp.main;

import io.codeforall.bootcamp.grid.Grid;
import io.codeforall.bootcamp.grid.GridPosition;
import io.codeforall.bootcamp.pointer.Pointer;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;

public class Game implements KeyboardHandler {
    private Grid grid;
    private Pointer pointer;
    private Keyboard keyboard;

    public Game() throws FileNotFoundException {
        this.grid = new Grid();
        this.pointer = new Pointer(new GridPosition(this.grid, 0, 0), this.grid.getCellSize(), this.grid.getPaintableRectangles());
        this.keyboard = new Keyboard(this);
        createKeyboardEvents();
    }

    public void initGame() {
        this.grid.initGrid();
        this.pointer.initPointer();
    }

    public void saveDraw() throws IOException {
        File file = new File("/Users/codecadet/Documents/My-Projects/Paint/saved/saved.txt");
        FileWriter fileW = new FileWriter(file);
        BufferedWriter buffW = new BufferedWriter(fileW);
        buffW.write(this.grid.gridStatus());
        buffW.close();
    }

    public void loadLastDraw() throws IOException {
        char[] savedGridPosition = new char[this.grid.gridStatus().length()];
        FileReader fileR = new FileReader("/Users/codecadet/Documents/My-Projects/Paint/saved/saved.txt");
        BufferedReader buffW = new BufferedReader(fileR);
        buffW.read(savedGridPosition);
        for (int i = 0; i < savedGridPosition.length; i++) {
            if (savedGridPosition[i] == 1){
                this.grid.getPaintableRectangles().get(i).setColor(Color.BLACK);
                this.grid.getPaintableRectangles().get(i).fill();
                this.grid.getPaintableRectangles().get(i).setPainted(true);
            }
        }
        buffW.close();
    }

    public void createKeyboardEvents() {
        KeyboardEvent keyboardEventS = new KeyboardEvent();
        keyboardEventS.setKey(KeyboardEvent.KEY_S);
        keyboardEventS.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventS);

        KeyboardEvent keyboardEventL = new KeyboardEvent();
        keyboardEventL.setKey(KeyboardEvent.KEY_L);
        keyboardEventL.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventL);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_S:
                try {
                    saveDraw();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case KeyboardEvent.KEY_L:
                try {
                    loadLastDraw();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
