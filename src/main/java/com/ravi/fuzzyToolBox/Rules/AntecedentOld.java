package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.Tnorm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class AntecedentOld implements Antecedent{
    private FuzzySet input;
    private List<MemFunc> memFuncs;

    public AntecedentOld(FuzzySet input, List<MemFunc> memFuncs) {
        this.input = input;
        this.memFuncs = memFuncs;
    }

    public AntecedentOld(FuzzySet input, MemFunc memFunc) {
        this.input = input;
        this.memFuncs = new ArrayList<MemFunc>();
        this.memFuncs.add(memFunc);
    }

    public MemFunc getUpperMemFunction(){
        for(MemFunc memFunc : memFuncs){
            if(memFunc.isUpper()){
                return memFunc;
            }
        }

        return null;
    }

    public MemFunc getLowerMemFunction(){
        for(MemFunc memFunc : memFuncs){
            if(memFunc.isType2() && ! memFunc.isUpper()){
                return memFunc;
            }
        }

        return null;
    }

    public Map<String, Double> getFiringLevel(double x, FZOperation fuzzyOperation){
        Map<String, Double> firingLevels = new HashMap<String, Double>();
        for(MemFunc memFunc : memFuncs){
            String type = "";
            if(memFunc.isType2() && memFunc.isUpper()){
                type = Rule.upper;
            }else if(memFunc.isType2()){
                type = Rule.lower;
            }else{
                type = Rule.regular;
            }
            firingLevels.put(type, fuzzyOperation.run(input, memFunc, x));
        }
        return firingLevels;
    }

    @Override
    public Map<String, Double> getFiringLevel(FuzzySet x, FZOperation fuzzyOperation) {
        return getFiringLevel(x.getValue(), fuzzyOperation);
    }
}
