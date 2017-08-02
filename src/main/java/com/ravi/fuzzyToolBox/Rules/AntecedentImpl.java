package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.FuzzySets.InputFuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 611445924 on 11/06/2017.
 */
public class AntecedentImpl implements Antecedent{
    private List<MemFunc> memFuncs;


    public AntecedentImpl(List<MemFunc> memFuncs) {
        this.memFuncs = memFuncs;
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

    @Override
    public MemFunc getMemFuncion() {
        return memFuncs.get(0);
    }

    @Override
    public boolean isType2() {
        for(MemFunc memFunc : memFuncs){
            if(memFunc.isType2()){
                return true;
            }
        }

        return false;
    }

    public Map<String, Double> getFiringLevel(double x, FZOperation fuzzyOperation) {
        return getFiringLevel(new InputFuzzySet(0, x), fuzzyOperation);
    }

    public Map<String, Double> getFiringLevel(FuzzySet x, FZOperation fuzzyOperation){
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
            firingLevels.put(type, fuzzyOperation.run(x, memFunc));
        }
        return firingLevels;
    }
}
