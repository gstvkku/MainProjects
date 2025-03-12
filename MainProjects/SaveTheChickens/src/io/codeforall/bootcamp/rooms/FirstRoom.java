package io.codeforall.bootcamp.rooms;

import io.codeforall.bootcamp.charactes.Player;
import io.codeforall.bootcamp.field.Field;
import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.prop.Chest;
import io.codeforall.bootcamp.prop.Door;
import io.codeforall.bootcamp.prop.Prop;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;


public class FirstRoom extends Room implements KeyboardHandler {

    private Field field;
    private Chest[] chestArray;
    private Door door;
    private Keyboard keyboard;
    private KeyboardEvent keyboardEventSpace;
    private KeyboardEvent keyboardEventE;
    private Player player;
    private int chestWithKey;

    public FirstRoom(int padding, Field field, Player player) {
        super(padding);
        this.field = field;
        this.player = player;
        chestArray = new Chest[15];
        this.door = new Door(new FieldPosition(field, 38, 1));
        keyboard = new Keyboard(this);
        this.keyboardEventSpace = new KeyboardEvent();
        this.keyboardEventE = new KeyboardEvent();
        createKeyboardEvents();
    }

    public void initRoomProps() {
        this.chestArray[0] = new Chest(new FieldPosition(field, 45, 40), false);
        this.chestArray[1] = new Chest(new FieldPosition(field, 7, 7), false);
        this.chestArray[2] = new Chest(new FieldPosition(field, 7, 70), false);
        this.chestArray[3] = new Chest(new FieldPosition(field, 70, 7), false);
        this.chestArray[4] = new Chest(new FieldPosition(field, 70, 70), false);
        this.chestArray[5] = new Chest(new FieldPosition(field, 28, 12), false);
        this.chestArray[6] = new Chest(new FieldPosition(field, 16, 64), false);
        this.chestArray[7] = new Chest(new FieldPosition(field, 58, 30), false);
        this.chestArray[8] = new Chest(new FieldPosition(field, 30, 55), false);
        this.chestArray[9] = new Chest(new FieldPosition(field, 57, 51), false);
        this.chestArray[10] = new Chest(new FieldPosition(field, 30, 42), false);
        this.chestArray[11] = new Chest(new FieldPosition(field, 50, 17), false);
        this.chestArray[12] = new Chest(new FieldPosition(field, 15, 45), false);
        this.chestArray[13] = new Chest(new FieldPosition(field, 25, 25), false);
        this.chestArray[14] = new Chest(new FieldPosition(field, 55, 65), false);
        for (Chest chest : chestArray) {
            chest.initProp();
        }
        this.door.initProp();
    }

    @Override
    public void initRoom() {
        getBackground().draw();
        initRoomProps();
        randomHasTheKey();
    }

    public boolean verifyChestIntersection(Prop prop) {
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

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public void createKeyboardEvents() {
        keyboardEventSpace.setKey(KeyboardEvent.KEY_SPACE);
        keyboardEventSpace.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventSpace);

        keyboardEventE.setKey(KeyboardEvent.KEY_E);
        keyboardEventE.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventE);
    }

    public void deleteKeyboardEvents() {
        keyboard.removeEventListener(keyboardEventSpace);
        keyboard.removeEventListener(keyboardEventE);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_SPACE:
                for (Chest chest : chestArray) {
                    if (verifyChestIntersection(chest)) {
                        chest.open(true);
                        player.getPlayerIcon().delete();
                        player.getPlayerIcon().draw();
                    }
                }
                if (verifyChestIntersection(this.door) && this.player.isHasKey())
                {
                    this.player.setFinishChallenge(true);
                }
                break;

            case KeyboardEvent.KEY_E:
                if (verifyChestIntersection(chestArray[chestWithKey]) && chestArray[chestWithKey].isOpen()) {
                    chestArray[chestWithKey].getKey();
                    player.setHasKey(true);
                    player.getPlayerIcon().delete();
                    player.getPlayerIcon().draw();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }

    //the key will appear in a random chest
    public void randomHasTheKey() {
        this.chestWithKey = (int) (Math.random() * 15);
        this.chestArray[chestWithKey].setHasTheKey(true);
    }
}

