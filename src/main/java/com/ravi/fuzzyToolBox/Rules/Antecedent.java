package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.Tnorm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class Antecedent {
    private FuzzySet input;
    private List<MemFunc> memFuncs;

    public Antecedent(FuzzySet input, List<MemFunc> memFuncs) {
        this.input = input;
        this.memFuncs = memFuncs;
    }

    public Antecedent(FuzzySet input, MemFunc memFunc) {
        this.input = input;
        this.memFuncs = new ArrayList<MemFunc>();
        this.memFuncs.add(memFunc);
    }

    public List<Double> getFiringLevel(double x, FZOperation fuzzyOperation){
        List<Double> firingLevels = new ArrayList<Double>();
        for(MemFunc memFunc : memFuncs){
            firingLevels.add(fuzzyOperation.run(input, memFunc, x));
        }
        return firingLevels;
    }
}
