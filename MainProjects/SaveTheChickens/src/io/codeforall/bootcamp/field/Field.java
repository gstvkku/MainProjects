package io.codeforall.bootcamp.field;

import io.codeforall.bootcamp.rooms.Room;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Field {
    private final int PADDING;
    private final Rectangle rectangle;
    private Room room;
    private int cols;
    private int rows;
    private int width;
    private int height;
    private int cellSize;

    public Field(int cols, int rows) {
        PADDING = 10;
        this.cellSize = 10;
        this.cols = cols;
        this.rows = rows;
        this.width = cols * this.cellSize;
        this.height = rows * this.cellSize;
        this.rectangle = new Rectangle(PADDING, PADDING, this.width, this.height);
    }

    public void initField(Room room) {
        this.room = room;
        this.room.initRoom();
    }

    public int getPADDING() {
        return PADDING;
    }

    public int getCellSize() {
        return cellSize;
    }



}
