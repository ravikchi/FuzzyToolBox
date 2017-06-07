package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 29/05/2017.
 */
public class RulesInputs {
    private FuzzySet x = new FuzzySetImpl(9);
    private FuzzySet y = new FuzzySetImpl(9);
    private boolean type2 = false;


    private List<MemFunc> low = new ArrayList<MemFunc>();
    private List<MemFunc> mid = new ArrayList<MemFunc>();
    private List<MemFunc> high = new ArrayList<MemFunc>();

    private MemFunc input;

    private List<MemFunc> lowConseqent = new ArrayList<MemFunc>();
    private List<MemFunc> midConseqent = new ArrayList<MemFunc>();
    private List<MemFunc> highConseqent = new ArrayList<MemFunc>();

    public RulesInputs(FuzzySet x, FuzzySet y, double start) {
        this.x = x;
        this.y = y;
        input = new TrapezoidalMemFunc("input",x.getLSupport(start), start, start, x.getRSupport(start), false, false);
    }

    public MemFunc getInput() {
        return input;
    }

    public void setInput(MemFunc input) {
        this.input = input;
    }

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

    public List<MemFunc> getLow() {
        return low;
    }

    public void setLow(List<MemFunc> low) {
        this.low = low;
    }

    public List<MemFunc> getMid() {
        return mid;
    }

    public void setMid(List<MemFunc> mid) {
        this.mid = mid;
    }

    public List<MemFunc> getHigh() {
        return high;
    }

    public void setHigh(List<MemFunc> high) {
        this.high = high;
    }

    public List<MemFunc> getLowConseqent() {
        return lowConseqent;
    }

    public void setLowConseqent(List<MemFunc> lowConseqent) {
        this.lowConseqent = lowConseqent;
    }

    public List<MemFunc> getMidConseqent() {
        return midConseqent;
    }

    public void setMidConseqent(List<MemFunc> midConseqent) {
        this.midConseqent = midConseqent;
    }

    public List<MemFunc> getHighConseqent() {
        return highConseqent;
    }

    public void setHighConseqent(List<MemFunc> highConseqent) {
        this.highConseqent = highConseqent;
    }
}
