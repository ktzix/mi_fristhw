package hu.bme.jschneid.maze;

import java.math.BigInteger;

public class MazeRule {

    private int rules;

    private boolean borderNorth;
    private boolean borderSouth;
    private boolean borderWest;
    private boolean borderEast;
    private boolean item;

    private int row;
    private int col;

    private boolean start;
    private boolean end;

    public MazeRule(int row, int col, int rules) {
        this.rules = rules;
        this.row = row;
        this.col = col;
        BigInteger ruleNumber = BigInteger.valueOf(rules);
        borderNorth = ruleNumber.testBit(0);
        borderEast = ruleNumber.testBit(1);
        borderSouth = ruleNumber.testBit(2);
        borderWest = ruleNumber.testBit(3);
        item = ruleNumber.testBit(4);
    }

    public boolean isBorderNorth() {
        return borderNorth;
    }

    public boolean isBorderSouth() {
        return borderSouth;
    }

    public boolean isBorderWest() {
        return borderWest;
    }

    public boolean isBorderEast() {
        return borderEast;
    }

    public boolean isItem() {
        return item;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}