package com.kristindragos.fourwords;

import java.util.Collections;
import java.util.List;

/**
 * Created by kristin on 1/14/17.
 */

public class GameCube {
    private int row;
    private int column;
    private List<String> values;
    private String currentValue;
    private boolean locked;

    public GameCube(List<String> values) {
        this.values = values;
        rollDice();
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String rollDice() {
        if(!isLocked()) {
            Collections.shuffle(values);
            setCurrentValue(values.get(0));
        }
        return currentValue;
    }
}
