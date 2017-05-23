package com.ravi.fuzzyToolBox.FuzzySets;

/**
 * Created by rc16956 on 23/05/2017.
 */
public class FuzzySetImpl implements FuzzySet {
    private double xQuote;
    private double spread;

    public FuzzySetImpl(double spread) {
        this.spread = spread;
    }

    public double getxQuote() {
        return xQuote;
    }

    public void setxQuote(double xQuote) {
        this.xQuote = xQuote;
    }
}
