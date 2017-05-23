package com.ravi.fuzzyToolBox.Fuzzyfier;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;

/**
 * Created by rc16956 on 23/05/2017.
 */
public class SingletonFuzzyfier implements Fuzzifier {
    private double start;
    private double end;
    private double spread;

    public SingletonFuzzyfier(double start, double end, double spread) {
        this.start = start;
        this.end = end;
        this.spread = spread;
    }

    public FuzzySet fuzzify(double x) {
        FuzzySet set = new FuzzySetImpl(spread);
        if(x >= start && x <= end){
            set.setxQuote(x);
        }else{
            set.setxQuote(0);
        }

        return set;
    }
}
