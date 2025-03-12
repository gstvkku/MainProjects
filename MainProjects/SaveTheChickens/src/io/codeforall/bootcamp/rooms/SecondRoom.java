package io.codeforall.bootcamp.rooms;

import io.codeforall.bootcamp.charactes.Player;
import io.codeforall.bootcamp.field.Field;
import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.field.Grid;
import io.codeforall.bootcamp.prop.Chest;
import io.codeforall.bootcamp.prop.Door;
import io.codeforall.bootcamp.prop.Prop;
import io.codeforall.bootcamp.prop.Snake;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class SecondRoom extends Room implements KeyboardHandler {

    private Field field;
    private Snake[] snakesArray;
    private Door door;
    private Keyboard keyboard;
    private Player player;
    private boolean playerFinishChallenge;
    private KeyboardEvent keyboardEventSpace;

    public SecondRoom(int padding, Field field, Player player) {
        super(padding);
        this.field = field;
        this.player = player;
        snakesArray = new Snake[30];
        this.door = new Door(new FieldPosition(field, 38, 1));
        this.keyboard = new Keyboard(this);
        playerFinishChallenge = false;
        keyboardEventSpace = new KeyboardEvent();
        createKeyboardEvents();
    }

    @Override
    public void initRoomProps() {
        this.snakesArray[0] = new Snake(new FieldPosition(field, 7, 60));
        this.snakesArray[1] = new Snake(new FieldPosition(field, 70, 45));
        this.snakesArray[2] = new Snake(new FieldPosition(field, 7, 30));
        this.snakesArray[3] = new Snake(new FieldPosition(field, 70, 15));
        this.snakesArray[4] = new Snake(new FieldPosition(field, 13, 34));
        this.snakesArray[5] = new Snake(new FieldPosition(field, 26, 65));
        this.snakesArray[6] = new Snake(new FieldPosition(field, 54, 20));
        this.snakesArray[7] = new Snake(new FieldPosition(field, 32, 30));
        this.snakesArray[8] = new Snake(new FieldPosition(field, 40, 43));
        this.snakesArray[9] = new Snake(new FieldPosition(field, 51, 45));
        this.snakesArray[10] = new Snake(new FieldPosition(field, 12, 12));
        this.snakesArray[11] = new Snake(new FieldPosition(field, 70, 15));
        this.snakesArray[12] = new Snake(new FieldPosition(field, 40, 10));
        this.snakesArray[13] = new Snake(new FieldPosition(field, 33, 10));
        this.snakesArray[14] = new Snake(new FieldPosition(field, 54, 20));
        this.snakesArray[15] = new Snake(new FieldPosition(field, 13, 55));
        this.snakesArray[16] = new Snake(new FieldPosition(field, 20, 53));
        this.snakesArray[17] = new Snake(new FieldPosition(field, 52, 52));
        this.snakesArray[18] = new Snake(new FieldPosition(field, 48, 15));
        this.snakesArray[19] = new Snake(new FieldPosition(field, 34, 43));
        this.snakesArray[20] = new Snake(new FieldPosition(field, 60, 30));
        this.snakesArray[21] = new Snake(new FieldPosition(field, 57, 38));
        this.snakesArray[22] = new Snake(new FieldPosition(field, 63, 68));
        this.snakesArray[23] = new Snake(new FieldPosition(field, 30, 58));
        this.snakesArray[24] = new Snake(new FieldPosition(field, 52, 57));
        this.snakesArray[25] = new Snake(new FieldPosition(field, 17, 45));
        this.snakesArray[26] = new Snake(new FieldPosition(field, 40, 24));
        this.snakesArray[27] = new Snake(new FieldPosition(field, 17, 18));
        this.snakesArray[28] = new Snake(new FieldPosition(field, 34, 43));
        this.snakesArray[29] = new Snake(new FieldPosition(field, 13, 55));
        for (Snake snake : snakesArray) {
            snake.initProp();
        }
        this.door.initProp();
    }

    public void deleteKeyboardEvents() {
        keyboard.removeEventListener(keyboardEventSpace);
    }

    @Override
    public void initRoom() {
        getBackground().draw();
        initRoomProps();
    }

    public void verifyIfTouchSnakes() {
        for (int i = 0; i < snakesArray.length; i++) {
            if (verifyIntersectionSecond(snakesArray[i])) {
                this.player.killPlayer();
                return;
            }
        }
    }

    public boolean isPlayerFinishChallenge() {
        return playerFinishChallenge;
    }


    public boolean verifyIntersectionSecond(Prop prop) {
        if (prop != null) {
            if (this.player.getFieldPosition().getX() + 25 > prop.getFieldPosition().getX() &&
                    this.player.getFieldPosition().getX() + 25 < prop.getFieldPosition().getX() + 50 &&
                    this.player.getFieldPosition().getY() + 42 > prop.getFieldPosition().getY() &&
                    this.player.getFieldPosition().getY() + 42 < prop.getFieldPosition().getY() + 50) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void createKeyboardEvents() {
        keyboardEventSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboardEventSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventSpace);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_SPACE:
                if (verifyIntersectionSecond(this.door) && this.player.isHasKey()) {
                    player.setFinishChallenge(true);
                    this.playerFinishChallenge = true;
                }
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
