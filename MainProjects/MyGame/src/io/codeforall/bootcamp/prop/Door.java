package io.codeforall.bootcamp.prop;

import io.codeforall.bootcamp.field.FieldPosition;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Door extends Prop {

    private Picture doorIcon;
    private boolean isOpen;
    public Door(FieldPosition fieldPosition) {
        super(fieldPosition);
        isOpen = false;
        doorIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "resources/Objects/theDoor_resized.png");
    }

    public boolean isOpen() {
        return isOpen;
    }

    public Picture getDoorIcon() {
        return doorIcon;
    }

    @Override
    public void initProp() {
        this.doorIcon.draw();
    }

}
