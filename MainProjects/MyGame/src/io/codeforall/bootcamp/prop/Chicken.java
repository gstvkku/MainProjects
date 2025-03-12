package io.codeforall.bootcamp.prop;

import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.main.Music;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Chicken extends Prop{

    private Picture chickenIcon;
    private boolean isCaught;
    private Music chicken;


    public Chicken(FieldPosition fieldPosition) {
        super(fieldPosition);
        this.chickenIcon = new Picture(fieldPosition.getX(), fieldPosition.getY(), "resources/Objects/chicken.png");
    }

    @Override
    public void initProp() {
        this.chickenIcon.draw();
    }

    public void isCaught(boolean b){
        if (b == true){
            chickenIcon.delete();
            chicken = new Music("resources/Music/chickendead.wav");
            chicken.play();
        }
    }

    public Music getChickenMusic() {
        return chicken;
    }
}
