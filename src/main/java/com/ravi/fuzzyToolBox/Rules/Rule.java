package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class Rule {
    private List<Antecedent> antecedents = new ArrayList<Antecedent>();
    private List<Consequent> consequents = new ArrayList<Consequent>();
    private String name;

    public Rule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Antecedent> getAntecedents() {
        return antecedents;
    }

    public void addAntecedent(Antecedent antecedent){
        antecedents.add(antecedent);
    }

    public List<Consequent> getConsequents() {
        return consequents;
    }

    public void addConsequent(Consequent consequent){
        this.consequents.add(consequent);
    }

    public void calculateFiringLevels(List<Double> inputs, FZOperation fzOperation){
        for(int i=0; i<antecedents.size(); i++){
            Antecedent a = antecedents.get(i);
            double ipt = inputs.get(i);
            a.getFiringLevel(ipt, fzOperation);
        }
    }
}
