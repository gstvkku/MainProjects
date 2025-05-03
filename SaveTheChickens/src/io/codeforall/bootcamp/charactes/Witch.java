package io.codeforall.bootcamp.charactes;

import io.codeforall.bootcamp.field.FieldPosition;
import io.codeforall.bootcamp.prop.Prop;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Witch extends Prop {

    Picture witchIcon;
    public Witch(FieldPosition fieldPosition) {
        super(fieldPosition);
        witchIcon = new Picture(70, 70, "resources/Objects/witch100x120.png");
    }

    public Picture getWitchIcon() {
        return witchIcon;
    }
}
