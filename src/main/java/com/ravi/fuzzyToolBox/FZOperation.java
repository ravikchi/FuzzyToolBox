package com.ravi.fuzzyToolBox;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

/**
 * Created by 611445924 on 26/05/2017.
 */
public interface FZOperation {
    public double run(FuzzySet input, MemFunc memFunc, double x);
}