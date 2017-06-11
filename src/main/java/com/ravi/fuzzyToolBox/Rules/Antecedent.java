package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.Map;

/**
 * Created by 611445924 on 11/06/2017.
 */
public interface Antecedent {

    public MemFunc getUpperMemFunction();
    public MemFunc getLowerMemFunction();
    public Map<String, Double> getFiringLevel(double x, FZOperation fuzzyOperation);
    public Map<String, Double> getFiringLevel(FuzzySet x, FZOperation fuzzyOperation);
}
