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
    public int[][] getCount(Rules rules, FZOperation fzOperation) {
        int columns = 51;
        int rows = 51;

        int[][] counts = new int[rows][columns];
        for(int i=0; i<counts.length; i++){
            for(int j=0; j<counts[i].length; j++) {
                for (int k = 0; k < rules.size(); k++) {
                    Rule rule = rules.getRule(k);
                    Antecedent antecedent = rule.getAntecedents().get(0);
                    double firingLevel1 = antecedent.getFiringLevel(i, fzOperation);
                    antecedent = rule.getAntecedents().get(1);
                    double firingLevel2 = antecedent.getFiringLevel(j, fzOperation);

                    if(firingLevel1 > 0 && firingLevel2 > 0) {
                        int rlCount = counts[i][j];
                        rlCount++;

                        counts[i][j] = rlCount;

                        System.out.println(rule.getName());
                        System.out.println(" Input levels 1 : "+i+" input level 2 : "+j);
                        System.out.println(firingLevel1);
                        System.out.println(firingLevel2);
                    }
                }
            }
        }

        return counts;

    }

    public Rules getRules(RulesInputs inputs){

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
