package io.codeforall.bootcamp.pointer;

import io.codeforall.bootcamp.grid.GridPosition;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.util.ArrayList;

public class Pointer implements KeyboardHandler {
    private GridPosition gridPosition;
    private Keyboard keyboard;
    private Rectangle pointerRectangle;
    private int size;
    private ArrayList<PaintableRectangle> paintedRectangles;

    public Pointer(GridPosition gridPosition, int size, ArrayList<PaintableRectangle> paintedRectangles) {
        this.gridPosition = gridPosition;
        this.keyboard = new Keyboard(this);
        this.size = size;
        this.pointerRectangle = new Rectangle(this.gridPosition.getX(), this.gridPosition.getY(), size, size);
        this.paintedRectangles = paintedRectangles;
        createKeyboardEvents();
    }

    public void initPointer() {
        this.pointerRectangle.setColor(Color.GREEN);
        this.pointerRectangle.fill();
    }

    public void move(KeyboardEvent keyboardEvent) {
        int distanceToMove;
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                if (gridPosition.getX() <= 20) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = -20;
                }
                this.pointerRectangle.translate(distanceToMove, 0);
                this.gridPosition.setX(this.pointerRectangle.getX());
                break;
            case KeyboardEvent.KEY_RIGHT:
                if (gridPosition.getX() >= 780) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = 20;
                }
                this.pointerRectangle.translate(distanceToMove, 0);
                this.gridPosition.setX(this.pointerRectangle.getX());
                break;
            case KeyboardEvent.KEY_UP:
                if (gridPosition.getY() == 10) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = -20;
                }
                this.pointerRectangle.translate(0, distanceToMove);
                this.gridPosition.setY(this.pointerRectangle.getY());
                break;
            case KeyboardEvent.KEY_DOWN:
                if (gridPosition.getY() >= 780) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = 20;
                }
                this.pointerRectangle.translate(0, distanceToMove);
                this.gridPosition.setY(this.pointerRectangle.getY());
                break;
        }
    }

    public boolean verifyIfSamePosition(PaintableRectangle rectangle) {
        if (this.gridPosition.getX() == rectangle.getX() &&
                this.gridPosition.getY() == rectangle.getY()) {
            return true;
        }
        return false;
    }


    public void paint() {
        for (PaintableRectangle rectangle : paintedRectangles) {
            if (verifyIfSamePosition(rectangle)) {
                if (rectangle.isPainted()) {
                    erase(rectangle);
                    rectangle.setPainted(false);
                    return;
                } else {
                    rectangle.setColor(Color.BLACK);
                    rectangle.fill();
                    rectangle.setPainted(true);
                    updatePointer();
                    return;
                }
            }
        }
    }


    public void erase(PaintableRectangle rectangle) {
        rectangle.delete();
        rectangle.setColor(Color.LIGHT_GRAY);
        rectangle.draw();
    }

    public void clean() {
        for (PaintableRectangle rectangle : paintedRectangles) {
            erase(rectangle);
        }
    }

    public void updatePointer() {
        this.pointerRectangle.delete();
        this.pointerRectangle = new Rectangle(this.gridPosition.getX(), this.gridPosition.getY(), size, size);
        this.pointerRectangle.setColor(Color.GREEN);
        this.pointerRectangle.fill();
    }


    public void createKeyboardEvents() {
        KeyboardEvent keyboardEventRight = new KeyboardEvent();
        keyboardEventRight.setKey(KeyboardEvent.KEY_RIGHT);
        keyboardEventRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventRight);

        KeyboardEvent keyboardEventLeft = new KeyboardEvent();
        keyboardEventLeft.setKey(KeyboardEvent.KEY_LEFT);
        keyboardEventLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventLeft);

        KeyboardEvent keyboardEventUp = new KeyboardEvent();
        keyboardEventUp.setKey(KeyboardEvent.KEY_UP);
        keyboardEventUp.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventUp);

        KeyboardEvent keyboardEventDown = new KeyboardEvent();
        keyboardEventDown.setKey(KeyboardEvent.KEY_DOWN);
        keyboardEventDown.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventDown);

        KeyboardEvent keyboardEventSpace = new KeyboardEvent();
        keyboardEventSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboardEventSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventSpace);

        KeyboardEvent keyboardEventC = new KeyboardEvent();
        keyboardEventC.setKey(KeyboardEvent.KEY_C);
        keyboardEventC.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventC);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
            case KeyboardEvent.KEY_RIGHT:
            case KeyboardEvent.KEY_UP:
            case KeyboardEvent.KEY_DOWN:
                move(keyboardEvent);
                break;
            case KeyboardEvent.KEY_SPACE:
                paint();
                break;
            case KeyboardEvent.KEY_C:
                clean();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
