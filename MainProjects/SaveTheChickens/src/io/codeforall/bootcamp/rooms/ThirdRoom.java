package io.codeforall.bootcamp.rooms;

import io.codeforall.bootcamp.charactes.Player;
import io.codeforall.bootcamp.charactes.Witch;
import io.codeforall.bootcamp.field.Field;
import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.prop.Chicken;
import io.codeforall.bootcamp.prop.Door;
import io.codeforall.bootcamp.prop.Prop;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class ThirdRoom extends Room implements KeyboardHandler {

    private Field field;
    private Door door;
    private Keyboard keyboard;
    private KeyboardEvent keyboardEventE;
    private KeyboardEvent keyboardEventSpace;
    private Player player;
    private Chicken[] chickensArray;
    private Witch witch;
    private int notSavedChickens;


    public ThirdRoom(int padding, Field field, Player player) {
        super(padding);
        this.field = field;
        this.player = player;
        this.door = new Door(new FieldPosition(field, 38, 1));
        keyboard = new Keyboard(this);
        keyboardEventSpace = new KeyboardEvent();
        keyboardEventE = new KeyboardEvent();
        chickensArray = new Chicken[30];
        witch = new Witch(new FieldPosition(field, 10, 10));
        createKeyboardEvents();
        notSavedChickens = chickensArray.length;
    }


    @Override
    public void initRoomProps() {
        for (int i = 0; i < chickensArray.length; i++) {
            this.chickensArray[i] = new Chicken(new FieldPosition(field, ((int) (Math.random() * 60)) + 10, ((int) (Math.random() * 60)) + 10));
        }
        witch.getWitchIcon().draw();
        for (Chicken chicken : chickensArray) {
            chicken.initProp();
        }
        this.door.initProp();
    }

    @Override
    public void initRoom() {
        getBackground().draw();
        initRoomProps();
    }

    public void verifyIfTouchWitch() {
        if (verifyIntersectionThird(witch)) {
            this.player.killPlayer();
            return;
        }
    }

    public boolean verifyIntersectionThird(Prop prop) {
        if (prop != null) {
            if (this.player.getFieldPosition().getX() + 25 > prop.getFieldPosition().getX() &&
                    this.player.getFieldPosition().getX() + 25 < prop.getFieldPosition().getX() + 30 &&
                    this.player.getFieldPosition().getY() + 42 > prop.getFieldPosition().getY() &&
                    this.player.getFieldPosition().getY() + 42 < prop.getFieldPosition().getY() + 30) {
                return true;
            }
            return false;
        }
        return false;
    }

    public int getNotSavedChickens() {
        return notSavedChickens;
    }

    @Override
    public Picture getBackground() {
        return super.getBackground();
    }

    public void createKeyboardEvents() {

        keyboardEventE.setKey(KeyboardEvent.KEY_E);
        keyboardEventE.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(keyboardEventE);

    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_E:
                for (Chicken chicken : chickensArray) {
                    if (verifyIntersectionThird(chicken)) {
                        notSavedChickens--;
                        chicken.isCaught(true);
                    }
                    if (notSavedChickens == 0) {
                        player.setTakeAllChickens();
                    }
                }
                break;

        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }

}
