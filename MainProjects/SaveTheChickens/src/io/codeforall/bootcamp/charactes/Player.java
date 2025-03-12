package io.codeforall.bootcamp.charactes;

import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.main.Game;
import io.codeforall.bootcamp.main.Music;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Player extends Character implements KeyboardHandler {
    private Picture playerIcon;
    // 50 X 85
    private Picture gameOverBackground;
    private Picture winnerBackground;
    private FieldPosition fieldPosition;
    private boolean finishChallenge;
    private boolean hasKey;
    private Keyboard keyboard;
    private boolean isAlive;
    private boolean wantRestart;
    private boolean isWinner;
    private boolean takeAllChickens;
    private Music dead;

    public Player(FieldPosition fieldPosition) {
        this.playerIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "resources/Player/kernelfront50x85.png");
        this.fieldPosition = fieldPosition;
        this.isAlive = true;
        this.wantRestart = false;
        this.finishChallenge = false;
        this.hasKey = false;
        this.isWinner = false;
        this.takeAllChickens = false;
        this.keyboard = new Keyboard(this);
        this.gameOverBackground = new Picture(10, 10, "resources/Backgrounds/gameover800x800.png");
        this.winnerBackground = new Picture(10, 10, "resources/Backgrounds/theend800x800.png");
        createKeyboardEvents();
    }

    public void initPlayer() {
        this.playerIcon.draw();
    }

    public void setTakeAllChickens() {
        this.takeAllChickens = true;
        this.playerIcon.delete();
        this.winnerBackground.draw();
    }

    public Picture getWinnerBackground() {
        return winnerBackground;
    }

    public boolean isWantRestart() {
        return wantRestart;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void killPlayer() {
        this.isAlive = false;
        this.playerIcon.delete();
        this.gameOverBackground.draw();
    }

    public void turnPlayerWinner() {
        this.isWinner = true;
    }

    public FieldPosition getFieldPosition() {
        return fieldPosition;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setFieldPosition(FieldPosition fieldPosition) {
        this.fieldPosition = fieldPosition;
    }

    public boolean isFinishChallenge() {
        return finishChallenge;
    }

    public void setFinishChallenge(boolean finishChallenge) {
        this.finishChallenge = finishChallenge;
    }

    public Picture getPlayerIcon() {
        return playerIcon;
    }

    public void setPlayerIcon(Picture playerIcon) {
        this.playerIcon = playerIcon;
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
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

        KeyboardEvent keyboardEventEnter = new KeyboardEvent();
        keyboardEventEnter.setKey(KeyboardEvent.KEY_ENTER);
        keyboardEventEnter.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventEnter);
    }

    //Falta adicionar as imagens do player virado para a esquerda, direita e cima
    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {


        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                this.playerIcon.delete();
                this.playerIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "imagem boneco para baixo");
                this.playerIcon.draw();
                break;
            case KeyboardEvent.KEY_RIGHT:
                this.playerIcon.delete();
                this.playerIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "imagem boneco para a esquerda");
                this.playerIcon.draw();
                break;
            case KeyboardEvent.KEY_UP:
                this.playerIcon.delete();
                this.playerIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "imagem boneco para cima");
                this.playerIcon.draw();
                break;
            case KeyboardEvent.KEY_DOWN:
                this.playerIcon.delete();
                this.playerIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "resources/kernelfront50x85.png");
                this.playerIcon.draw();
                break;
        }
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        int distanceToMove;
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                if (fieldPosition.getX() <= 60) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = -10;
                }
                this.playerIcon.translate(distanceToMove, 0);
                this.fieldPosition.setX(this.playerIcon.getX());
                break;
            case KeyboardEvent.KEY_RIGHT:
                if (fieldPosition.getX() >= 710) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = 10;
                }
                this.playerIcon.translate(distanceToMove, 0);
                this.fieldPosition.setX(this.playerIcon.getX());
                break;
            case KeyboardEvent.KEY_UP:
                if (fieldPosition.getY() == 10) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = -10;
                }
                this.playerIcon.translate(0, distanceToMove);
                this.fieldPosition.setY(this.playerIcon.getY());
                break;
            case KeyboardEvent.KEY_DOWN:
                if (fieldPosition.getY() >= 670) {
                    distanceToMove = 0;
                } else {
                    distanceToMove = 10;
                }
                this.playerIcon.translate(0, distanceToMove);
                this.fieldPosition.setY(this.playerIcon.getY());
                break;
            case KeyboardEvent.KEY_ENTER:
                if (takeAllChickens || !isAlive) {
                    this.wantRestart = true;
                }
        }
    }
}
