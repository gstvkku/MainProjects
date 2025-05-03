package io.codeforall.bootcamp.pointer;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class PaintableRectangle extends Rectangle {
    private boolean isPainted;

    public PaintableRectangle(double v, double v1, double v2, double v3) {
        super(v, v1, v2, v3);
        this.isPainted = false;
    }

    public boolean isPainted() {
        return isPainted;
    }

    public void setPainted(boolean painted) {
        isPainted = painted;
    }

}
