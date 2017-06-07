package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;
import com.ravi.fuzzyToolBox.Rules.Antecedent;
import com.ravi.fuzzyToolBox.Rules.Rule;
import com.ravi.fuzzyToolBox.Rules.Rules;
import com.ravi.fuzzyToolBox.Tnorm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 611445924 on 24/05/2017.
 */
public class FuzzyPartitions {

    private int[][] counts;
    private int[][] noveltyCounts;

    private Rules rules;
    private FZOperation fzOperation;

    public FuzzyPartitions(Rules rules, FZOperation fzOperation) {
        this.rules = rules;
        this.fzOperation = fzOperation;
        initCounts();
    }

    public int[][] getCounts() {
        return counts;
    }

    public int[][] getNoveltyCounts() {
        return noveltyCounts;
    }

    public void initCounts() {
        int columns = 50;
        int rows = 50;

        this.counts = new int[rows][columns];
        this.noveltyCounts = new int[rows][columns];
        for(int i=0; i<counts.length; i++){
            for(int j=0; j<counts[i].length; j++) {
                double[] lowerFiringLevels = new double[rules.size()];
                List<Double> inputs = new ArrayList<Double>();
                inputs.add((double) i);
                inputs.add((double) j);
                for (int k = 0; k < rules.size(); k++) {
                    Rule rule = rules.getRule(k);
                    Map<String, Double> firingLevel = rule.calculateFiringLevels(inputs, fzOperation);

                    if(firingLevel.get("Regular") == null) {
                        if(firingLevel.get("Upper") > 0 || firingLevel.get("Lower") > 0) {
                            int rlCount = counts[i][j];
                            rlCount++;

                            counts[i][j] = rlCount;
                        }

                        /*System.out.println(rule.getName());
                        System.out.println(" Input levels 1 : "+i+" input level 2 : "+j);
                        System.out.println(firingLevel.get("Upper"));
                        System.out.println(firingLevel.get("Lower"));*/
                    }else{
                        if(firingLevel.get("Regular") > 0) {
                            int rlCount = counts[i][j];
                            rlCount++;

                            counts[i][j] = rlCount;
                        }
                    }
                }
            }
        }

    }

    public static Rules getRules(RulesInputs inputs){

        Rules rules = new Rules();

        Rule rule = new Rule("Low x1, low x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getLow()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getLow()));
        rules.addRule(rule);

        rule = new Rule("Low x1, mid x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getLow()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getMid()));
        rules.addRule(rule);

        rule = new Rule("Low x1, high x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getLow()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getHigh()));
        rules.addRule(rule);


        rule = new Rule("Mid x1, low x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getMid()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getLow()));
        rules.addRule(rule);

        rule = new Rule("Mid x1, mid x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getMid()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getMid()));
        rules.addRule(rule);

        rule = new Rule("Mid x1, high x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getMid()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getHigh()));
        rules.addRule(rule);

        rule = new Rule("High x1, low x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getHigh()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getLow()));
        rules.addRule(rule);

        rule = new Rule("High x1, mid x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getHigh()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getMid()));
        rules.addRule(rule);

        rule = new Rule("High x1, high x2");
        rule.addAntecedent(new Antecedent(inputs.getX(), inputs.getHigh()));
        rule.addAntecedent(new Antecedent(inputs.getY(), inputs.getHigh()));
        rules.addRule(rule);

        return rules;
    }
}
