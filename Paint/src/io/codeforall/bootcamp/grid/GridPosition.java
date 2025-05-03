package io.codeforall.bootcamp.grid;

import io.codeforall.bootcamp.grid.Grid;

public class GridPosition {
    private Grid grid;
    private int col;
    private int row;
    private int x;
    private int y;
    public GridPosition( Grid grid, int col, int row) {
        this.grid = grid;
        this.col = col;
        this.row = row;
        this.x = grid.colToX(col);
        this.y = grid.rowToY(row);
    }
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
        this.x = grid.colToX(col);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
        this.y = grid.rowToY(row);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.col = grid.XToCol(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.row = grid.YToRow(y);
    }
}
