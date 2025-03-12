package io.codeforall.bootcamp.prop;

import io.codeforall.bootcamp.field.FieldPosition;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Snake extends Prop {
    private Picture snakeIcon;
    private String direction;

    public Snake(FieldPosition fieldPosition) {
        super(fieldPosition);
        this.direction = direction;
        this.snakeIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "resources/Objects/snake50x50.png");
    }

    public Picture getSnakeIcon() {
        return snakeIcon;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public void initProp() {
        this.snakeIcon.draw();
    }

    @Override
    public FieldPosition getFieldPosition() {
        return super.getFieldPosition();
    }
}


