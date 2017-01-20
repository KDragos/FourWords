package com.kristindragos.fourwords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by kristin on 1/14/17.
 */

public class GameBoard {
    private List<GameCube> availableDice;
    private List<List<GameCube>> board;

    public GameBoard() {
    }

    public List<GameCube> getAvailableDice() {
        return availableDice;
    }

    public void setAvailableDice(List<GameCube> availableDice) {
        this.availableDice = availableDice;
    }

    public void generateNewBoard() {
        // Create dice
        createAvailableDice();

        // Add them to board
        generateBoardLayout();
    }

    public String getCubeValueAt(int column, int row) {
        return board.get(column - 1).get(row - 1).getCurrentValue();
    }

    // Lays the dice on the board
    private void generateBoardLayout() {
        Collections.shuffle(availableDice);
        List<GameCube> row1 = Arrays.asList(availableDice.get(0), availableDice.get(1), availableDice.get(2), availableDice.get(3));
        List<GameCube> row2 = Arrays.asList(availableDice.get(4), availableDice.get(5), availableDice.get(6), availableDice.get(7));
        List<GameCube> row3 = Arrays.asList(availableDice.get(8), availableDice.get(9), availableDice.get(10), availableDice.get(11));
        List<GameCube> row4 = Arrays.asList(availableDice.get(12), availableDice.get(13), availableDice.get(14), availableDice.get(15));
        board = new ArrayList<List<GameCube>>();
        board.add(0, row1);
        board.add(1, row2);
        board.add(2, row3);
        board.add(3, row4);
    }

    // Creates the 16 dice.
    private void createAvailableDice() {
        availableDice = new ArrayList<GameCube>();
        List<String> dice1Values = Arrays.asList("R", "I", "F", "O", "B", "X");
        GameCube cube1 = new GameCube(dice1Values);
        availableDice.add(cube1);

        List<String> dice2Values = Arrays.asList("I", "F", "E", "H", "E", "Y");
        GameCube cube2 = new GameCube(dice2Values);
        availableDice.add(cube2);

        List<String> dice3values = Arrays.asList("E", "D", "N", "O", "W", "S");
        GameCube cube3 = new GameCube(dice3values);
        availableDice.add(cube3);

        List<String> dice4values = Arrays.asList("U", "T", "O", "K", "N", "D");
        GameCube cube4 = new GameCube(dice4values);
        availableDice.add(cube3);

        List<String> dice5values = Arrays.asList("H", "M", "S", "R", "A", "O");
        GameCube cube5 = new GameCube(dice5values);
        availableDice.add(cube5);

        List<String> dice6values = Arrays.asList("L", "U", "P", "E", "T", "S");
        GameCube cube6 = new GameCube(dice6values);
        availableDice.add(cube6);

        List<String> dice7values = Arrays.asList("A", "C", "I", "T", "O", "A");
        GameCube cube7 = new GameCube(dice7values);
        availableDice.add(cube7);

        List<String> dice8values = Arrays.asList("Y", "L", "G", "K", "U", "E");
        GameCube cube8 = new GameCube(dice8values);
        availableDice.add(cube8);

        List<String> dice9values = Arrays.asList("Qu", "B", "M", "J", "O", "A");
        GameCube cube9 = new GameCube(dice8values);
        availableDice.add(cube9);

        List<String> dice10values = Arrays.asList("E", "H", "I", "S", "P", "N");
        GameCube cube10 = new GameCube(dice10values);
        availableDice.add(cube10);

        List<String> dice11values = Arrays.asList("V", "E", "T", "I", "G", "N");
        GameCube cube11 = new GameCube(dice11values);
        availableDice.add(cube11);

        List<String> dice12values = Arrays.asList("B", "A", "L", "I", "Y", "T");
        GameCube cube12 = new GameCube(dice12values);
        availableDice.add(cube12);

        List<String> dice13values = Arrays.asList("E", "Z", "A", "V", "N", "D");
        GameCube cube13 = new GameCube(dice13values);
        availableDice.add(cube13);

        List<String> dice14values = Arrays.asList("R", "A", "L", "E", "S", "C");
        GameCube cube14 = new GameCube(dice14values);
        availableDice.add(cube14);

        List<String> dice15values = Arrays.asList("U", "W", "I", "L", "R", "G");
        GameCube cube15 = new GameCube(dice15values);
        availableDice.add(cube15);

        List<String> dice16values = Arrays.asList("P", "A", "C", "E", "M", "D");
        GameCube cube16 = new GameCube(dice16values);
        availableDice.add(cube16);

    }
}
