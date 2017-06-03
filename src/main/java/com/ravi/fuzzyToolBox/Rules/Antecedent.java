package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.Tnorm;

import java.util.List;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class Antecedent {
    private FuzzySet input;
    private MemFunc memFunc;

    public Antecedent(FuzzySet input, MemFunc memFunc) {
        this.input = input;
        this.memFunc = memFunc;
    }

    public List<Double> getFiringLevel(double x, FZOperation fuzzyOperation){
        return fuzzyOperation.run(input, memFunc, x);
    }
}
