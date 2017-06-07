package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.*;

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

    public double getClAvrgx(){
        double avg = 0.0;
        int n = 0;
        for(Consequent consequent: consequents){

            n++;
        }

        return avg/n;
    }

    public Map<String, Double> calculateFiringLevels(List<Double> inputs, FZOperation fzOperation){
        Map<String, Double> firingLevel = new HashMap<String, Double>();
        for(int i=0; i<antecedents.size(); i++){
            Antecedent a = antecedents.get(i);
            double ipt = inputs.get(i);

            Map<String, Double> anteceentFL = a.getFiringLevel(ipt, fzOperation);

            Iterator<String> it = anteceentFL.keySet().iterator();
            while(it.hasNext()){
                String key = it.next();
                if(firingLevel.get(key) == null){
                    firingLevel.put(key, anteceentFL.get(key));
                }else{
                    if(anteceentFL.get(key) < firingLevel.get(key)){
                        firingLevel.put(key, anteceentFL.get(key));
                    }
                }
            }
        }

        return firingLevel;
    }
}
