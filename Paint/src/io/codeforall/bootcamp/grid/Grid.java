package io.codeforall.bootcamp.grid;

import io.codeforall.bootcamp.pointer.PaintableRectangle;
import org.academiadecodigo.simplegraphics.graphics.Color;

import java.util.ArrayList;

public class Grid {
    private static final int CELL_SIZE = 20;
    private static final int COLS = 40;
    private static final int ROWS = 40;
    private static final int PADDING = 10;
    private ArrayList<PaintableRectangle> paintableRectangles;

    public Grid() {
        this.paintableRectangles = new ArrayList<>();
    }

    public void initGrid() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                PaintableRectangle cell = new PaintableRectangle(colToX(col), rowToY(row), CELL_SIZE, CELL_SIZE);
                cell.setColor(Color.LIGHT_GRAY);
                paintableRectangles.add(cell);
                cell.draw();
            }
        }
    }

    public String gridStatus() {
        char[] gridStatus = new char[paintableRectangles.size()];
        for (int i = 0; i < gridStatus.length; i++) {
          if (paintableRectangles.get(i).isPainted()) {
              gridStatus[i] = 1;
          } else {gridStatus[i] = 0;}
        }
        return String.copyValueOf(gridStatus);
    }

    public ArrayList<PaintableRectangle> getPaintableRectangles() {
        return paintableRectangles;
    }

    public static int colToX(int col) {
        return PADDING + col * CELL_SIZE;
    }

    public static int rowToY(int row) {
        return PADDING + row * CELL_SIZE;
    }

    public static int XToCol(int X) {
        return (X / CELL_SIZE) - PADDING;
    }

    public static int YToRow(int Y) {
        return (Y / CELL_SIZE) - PADDING;
    }

    public static int getCellSize() {
        return CELL_SIZE;
    }
}
