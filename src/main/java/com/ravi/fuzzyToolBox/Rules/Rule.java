package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.TypeReduction.CentriodIterMethod;

import java.util.*;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class Rule {
    private List<Antecedent> antecedents = new ArrayList<Antecedent>();
    private List<Consequent> consequents = new ArrayList<Consequent>();
    private String name;

    public static final String regular = "Regular";
    public static final String upper = "Upper";
    public static final String lower = "Lower";

    private double upperFiringLevel = 0.0;
    private double lowerFiringLevel = 0.0;

    private double firingLevel = 0.0;
    private boolean type2 = false;

    private double clAvg;
    private double crAvg;

    public double getUpperFiringLevel() {
        return upperFiringLevel;
    }

    public void setUpperFiringLevel(double upperFiringLevel) {
        this.upperFiringLevel = upperFiringLevel;
    }

    public double getLowerFiringLevel() {
        return lowerFiringLevel;
    }

    public void setLowerFiringLevel(double lowerFiringLevel) {
        this.lowerFiringLevel = lowerFiringLevel;
    }

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
        calculateClAvg();
        calculateCrAvg();
    }

    public void calculateFiringLevels(List<Double> inputs, FZOperation fzOperation){
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

        if(firingLevel.get(regular) == null){
            type2 = true;
            this.upperFiringLevel = firingLevel.get(upper);
            this.lowerFiringLevel = firingLevel.get(lower);
        }else{
            this.firingLevel = firingLevel.get(regular);
        }



    }

    public double getFiringLevel() {
        return firingLevel;
    }

    public void setFiringLevel(double firingLevel) {
        this.firingLevel = firingLevel;
    }

    public boolean isType2() {
        return type2;
    }

    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    public void calculateClAvg(){
        double cl = 0;
        int n = 0;
        for (Consequent consequent: consequents) {
            cl = cl + CentriodIterMethod.getInstance().getCl(consequent);
            n++;
        }

        this.clAvg= cl/n;
    }

    public double getClAvg(){
        return this.clAvg;
    }

    public void calculateCrAvg(){
        double cr = 0;
        int n = 0;
        for (Consequent consequent: consequents) {
            cr = cr + CentriodIterMethod.getInstance().getCr(consequent);
            n++;
        }

        this.crAvg= cr/n;
    }

    public double getCrAvg(){
        return this.crAvg;
    }
}
