package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.PWLMF;
import com.ravi.fuzzyToolBox.ProductTnorm;
import com.ravi.fuzzyToolBox.Rules.AntecedentImpl;
import com.ravi.fuzzyToolBox.Rules.Consequent;
import com.ravi.fuzzyToolBox.Rules.Rule;
import com.ravi.fuzzyToolBox.Rules.Rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 11/06/2017.
 */
public class NoveltyPartRules {
    public List<MemFunc> getMemFuncsInput1(){
        List<MemFunc> memFuncs = new ArrayList<MemFunc>();

        memFuncs.add(new PWLMF("Negative", -1, -1, -1, 0));
        memFuncs.add(new PWLMF("Negative", -1,-1,-1, 0, 0, 0.3));

        memFuncs.add(new PWLMF("Zero", -1, 0, 0, 1));
        memFuncs.add(new PWLMF("Zero", -1, 0, 0, 1, 0, 0.9));

        memFuncs.add(new PWLMF("Positive", 0, 1, 1,1));
        memFuncs.add(new PWLMF("Positive", 0, 1, 1,1, 0, 0.3));

        return memFuncs;
    }

    public List<MemFunc> getMemFuncsInput2(){
        List<MemFunc> memFuncs = new ArrayList<MemFunc>();

        memFuncs.add(new PWLMF("Negative", -1, -1, -1, 0));
        memFuncs.add(new PWLMF("Negative", -1,-1,-1, 0, 0, 0.2));

        memFuncs.add(new PWLMF("Zero", -1, 0, 0, 1));
        memFuncs.add(new PWLMF("Zero", -1, 0, 0, 1, 0, 0.9));

        memFuncs.add(new PWLMF("Positive", 0, 1, 1,1));
        memFuncs.add(new PWLMF("Positive", 0, 1, 1,1, 0, 0.2));

        return memFuncs;
    }

    public double[][] getConsequents(){
        double[][] values =  new double[3][3];

        values[0][0] = -1;
        values[0][1] = -0.5;
        values[0][2] = 0;

        values[1][0] = -0.5;
        values[1][1] = 0;
        values[1][2] = 0.5;

        values[2][0] = 0;
        values[2][1] = 0.5;
        values[2][2] = 1;

        return values;

    }

    public Rules getRules(){
        Rules rules = new Rules();

        List<MemFunc> input1MemFuncs = getMemFuncsInput1();
        List<MemFunc> input2MemFuncs = getMemFuncsInput2();

        double[][] cons = getConsequents();

        int addi = 0;
        int addj = 0;

        for (int i = 1; i <= 3; i++) {
            List<MemFunc> antcMemFuncs1 = new ArrayList<MemFunc>();
            antcMemFuncs1.add(input1MemFuncs.get(addi));
            addi++;
            antcMemFuncs1.add(input1MemFuncs.get(addi));
            addj = 0;
            for (int j = 1; j <= 3; j++) {

                List<MemFunc> antcMemFuncs2 = new ArrayList<MemFunc>();
                antcMemFuncs2.add(input1MemFuncs.get(addj));
                addj++;
                antcMemFuncs2.add(input1MemFuncs.get(addj));


                Rule rule = new Rule(input1MemFuncs.get(addi).getName() + input2MemFuncs.get(addj).getName());
                rule.addAntecedent(new AntecedentImpl(antcMemFuncs1));
                rule.addAntecedent(new AntecedentImpl(antcMemFuncs2));

                double consVal = cons[i-1][j-1];
                rule.addConsequent(new Consequent(new PWLMF("", consVal-1, consVal, consVal, consVal+1, false, false, 0,1)));

                rules.addRule(rule);
                addj++;
            }
            addi++;
        }

        return rules;

    }

    public static void main(String[] args) {
        NoveltyPartRules createRules = new NoveltyPartRules();
        Rules rules = createRules.getRules();

        System.out.println(rules);

        List<Double> inputs = new ArrayList<Double>();
        inputs.add(-1.0);
        inputs.add(-0.95);

        for (int i = 0; i < rules.getRules().size(); i++) {
            Rule rule = rules.getRule(i);
            rule.calculateFiringLevels(inputs, new ProductTnorm());

            System.out.println(rule.getUpperFiringLevel());
            System.out.println(rule.getLowerFiringLevel());
        }
    }
}
