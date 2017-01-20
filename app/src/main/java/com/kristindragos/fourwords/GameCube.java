package com.kristindragos.fourwords;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kristin Dragos on 1/14/17.
 *
 * GameCube holds the basic information about a gamecube on the gameboard.
 * Variables: (String) currentValue and a list of possible values (also strings) for the cube.
 */

class GameCube {
    private String currentValue;
    private List<String> values;

    GameCube(List<String> values) {
        this.values = values;
        rollDice();
    }

    String getCurrentValue() {
        return currentValue;
    }

    private void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    private void rollDice() {
        Collections.shuffle(values);
        setCurrentValue(values.get(0));
    }
}
