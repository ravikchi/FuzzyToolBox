package com.ravi.fuzzyToolBox;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.List;

/**
 * Created by 611445924 on 26/05/2017.
 */
public interface FZOperation {
    public double run(FuzzySet input, MemFunc memFunc, double x);
    public double run(MemFunc memFunc, MemFunc memFunc1, double x);
    public double operation(double x, double y);
}
