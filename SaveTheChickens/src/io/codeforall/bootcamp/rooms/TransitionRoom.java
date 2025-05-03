package io.codeforall.bootcamp.rooms;

import io.codeforall.bootcamp.charactes.Player;
import io.codeforall.bootcamp.field.Field;
import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.prop.Door;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class TransitionRoom extends Room {

    private Field field;
    private Door door;
    private Keyboard keyboard;
    private Player player;

    public TransitionRoom(int padding, Field field, Player player) {
        super(padding);
        this.field = field;
        this.player = player;
        this.door = new Door(new FieldPosition(field, 38, 1));
        this.setBackground(new Picture(padding, padding, "resources/Backgrounds/lavapath800x800.png"));
    }

}
