package io.codeforall.bootcamp.rooms;

import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public abstract class Room {
    private Picture background;

    public Room(int padding) {
        this.background = new Picture(padding, padding, "resources/Backgrounds/fasewithdoor (1).png");
    }

    public void initRoom() {
    }
    public void initRoomProps() {

    }
    public Picture getBackground() {
        return background;
    }

    public void setBackground(Picture background) {
        this.background = background;
    }

    public Room() {
        super();
    }

}
