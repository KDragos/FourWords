package com.kristindragos.fourwords;

/**
 * Created by kristin on 1/14/17.
 */

public class GameBoardSpot {
    private int column;
    private int row;
    private boolean locked;
    private GameCube cube;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public GameCube getCube() {
        return cube;
    }

    public void setCube(GameCube cube) {
        this.cube = cube;
    }
}
