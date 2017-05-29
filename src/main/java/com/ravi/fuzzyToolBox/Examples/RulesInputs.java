package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;

/**
 * Created by 611445924 on 29/05/2017.
 */
public class RulesInputs {
    private FuzzySet x = new FuzzySetImpl(9);
    private FuzzySet y = new FuzzySetImpl(9);


    private MemFunc low = new TrapezoidalMemFunc(0, 0, 10, 20);
    private MemFunc mid = new TrapezoidalMemFunc(11, 20, 30, 40);
    private MemFunc high = new TrapezoidalMemFunc(31, 40, 50, 50);

    public FuzzySet getX() {
        return x;
    }

    public void setX(FuzzySet x) {
        this.x = x;
    }

    public FuzzySet getY() {
        return y;
    }

    public void setY(FuzzySet y) {
        this.y = y;
    }

    public MemFunc getLow() {
        return low;
    }

    public void setLow(MemFunc low) {
        this.low = low;
    }

    public MemFunc getMid() {
        return mid;
    }

    public void setMid(MemFunc mid) {
        this.mid = mid;
    }

    public MemFunc getHigh() {
        return high;
    }

    public void setHigh(MemFunc high) {
        this.high = high;
    }
}
