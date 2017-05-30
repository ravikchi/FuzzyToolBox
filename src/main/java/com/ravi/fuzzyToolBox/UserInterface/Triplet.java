package com.ravi.fuzzyToolBox.UserInterface;

/**
 * Created by 611445924 on 29/05/2017.
 */
public class Triplet {
    private int row;
    private int start;
    private int end;

    public Triplet(int row, int start, int end) {
        this.row = row;
        this.start = start;
        this.end = end;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "row=" + row +
                ", start=" + start +
                ", end=" + end;
    }
}
