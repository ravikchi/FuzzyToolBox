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
    public static void main(String[] args) {
        FuzzySet inputx = new FuzzySetImpl(9);
        FuzzySet inputy = new FuzzySetImpl(9);


        MemFunc low = new TrapezoidalMemFunc(0, 0, 10, 20);
        MemFunc mid = new TrapezoidalMemFunc(11, 20, 30, 40);
        MemFunc high = new TrapezoidalMemFunc(31, 40, 50, 50);

        FZOperation fzOperation = new Tnorm();

        Rules rules = new Rules();

        Rule rule = new Rule("Low x1, low x2");
        rule.addAntecedent(new Antecedent(inputx, low));
        rule.addAntecedent(new Antecedent(inputy, low));
        rules.addRule(rule);

        rule = new Rule("Low x1, mid x2");
        rule.addAntecedent(new Antecedent(inputx, low));
        rule.addAntecedent(new Antecedent(inputy, mid));
        rules.addRule(rule);

        rule = new Rule("Low x1, high x2");
        rule.addAntecedent(new Antecedent(inputx, low));
        rule.addAntecedent(new Antecedent(inputy, high));
        rules.addRule(rule);


        rule = new Rule("Mid x1, low x2");
        rule.addAntecedent(new Antecedent(inputx, mid));
        rule.addAntecedent(new Antecedent(inputy, low));
        rules.addRule(rule);

        rule = new Rule("Mid x1, mid x2");
        rule.addAntecedent(new Antecedent(inputx, mid));
        rule.addAntecedent(new Antecedent(inputy, mid));
        rules.addRule(rule);

        rule = new Rule("Mid x1, high x2");
        rule.addAntecedent(new Antecedent(inputx, mid));
        rule.addAntecedent(new Antecedent(inputy, high));
        rules.addRule(rule);

        rule = new Rule("High x1, low x2");
        rule.addAntecedent(new Antecedent(inputx, high));
        rule.addAntecedent(new Antecedent(inputy, low));
        rules.addRule(rule);

        rule = new Rule("High x1, mid x2");
        rule.addAntecedent(new Antecedent(inputx, high));
        rule.addAntecedent(new Antecedent(inputy, mid));
        rules.addRule(rule);

        rule = new Rule("High x1, high x2");
        rule.addAntecedent(new Antecedent(inputx, high));
        rule.addAntecedent(new Antecedent(inputy, high));
        rules.addRule(rule);

        Map<String, Integer> counts = new HashMap<String, Integer>();




        for(int i=0; i<=50; i++){
            for(int x=0; x<=50; x++) {
                counts.put(i+"~"+x, 0);

                for (int j = 0; j < rules.size(); j++) {
                    rule = rules.getRule(j);
                    Antecedent antecedent = rule.getAntecedents().get(0);
                    double firingLevel1 = antecedent.getFiringLevel(i, fzOperation);
                    antecedent = rule.getAntecedents().get(1);
                    double firingLevel2 = antecedent.getFiringLevel(x, fzOperation);

                    if(firingLevel1 > 0 && firingLevel2 > 0) {
                        Integer noOfRules = counts.get(i+"~"+x);
                        noOfRules++;

                        counts.put(i+"~"+x, noOfRules);

                        System.out.println(rule.getName());
                        System.out.println(" Input levels 1 : "+i+" input level 2 : "+x);
                        System.out.println(firingLevel1);
                        System.out.println(firingLevel2);
                    }
                }
            }
        }

        for(int i=50; i>=0; i--) {
            String val = "";
            if(i!=0){
                val = String.format("%2d",i);
                System.out.print(val+" | ");
            }else{
                System.out.print("----");
                val = "  ";

            }
            for (int x = 1; x <= 50; x++) {
                //System.out.print(i+","+x+",");
                if(i!=0){
                    System.out.print(counts.get(i + "~" + x) + " ");
                }else {
                    System.out.print("--");
                }
            }

            System.out.println();
            if(i==0) {
                System.out.println("   |");
            }
        }

    }
}
