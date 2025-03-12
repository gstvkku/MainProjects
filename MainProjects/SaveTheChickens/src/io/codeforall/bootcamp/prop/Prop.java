package io.codeforall.bootcamp.prop;

import io.codeforall.bootcamp.field.FieldPosition;

public abstract class Prop {
    private FieldPosition fieldPosition;

    public Prop(FieldPosition fieldPosition) {
        this.fieldPosition = fieldPosition;
    }

    public void initProp() {
    }

    public FieldPosition getFieldPosition() {
        return fieldPosition;
    }
}
