package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;
import com.ravi.fuzzyToolBox.ProductTnorm;
import com.ravi.fuzzyToolBox.Rules.AntecedentOld;
import com.ravi.fuzzyToolBox.Rules.Consequent;
import com.ravi.fuzzyToolBox.Rules.Rule;
import com.ravi.fuzzyToolBox.TypeReduction.TypeReducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 08/06/2017.
 */
public class TippingExample {
    public static void main(String[] args) {
        MemFunc RancidUpper = new TrapezoidalMemFunc("RancidUpper", 0, 1, 1, 5, true, true);
        MemFunc RancidLower = new TrapezoidalMemFunc("RancidLower", 0, 0, 0.5, 4.5, true, false);
        List<MemFunc> rancid = new ArrayList<MemFunc>();
        rancid.add(RancidUpper);rancid.add(RancidLower);

        MemFunc DeliciousUpper = new TrapezoidalMemFunc("DeliciousUpper", 4, 8, 9, 9, true, true);
        MemFunc DeliciousLower = new TrapezoidalMemFunc("DeliciousLower", 4.5, 8.5, 9, 9, true, false);
        List<MemFunc> delicious = new ArrayList<MemFunc>();
        delicious.add(DeliciousUpper);delicious.add(DeliciousLower);

        MemFunc PoorServiceUpper = new TrapezoidalMemFunc("PoorServiceUpper", 0,0, 1,3, true, true);
        MemFunc PoorServiceLower = new TrapezoidalMemFunc("PoorServiceLower", 0,0,0.5,2.5, true, false);
        List<MemFunc> poorService = new ArrayList<MemFunc>();
        poorService.add(PoorServiceUpper);poorService.add(PoorServiceLower);

        MemFunc GoodServiceUpper = new TrapezoidalMemFunc("GoodServiceUpper", 1,3, 5,7, true, true);
        MemFunc GoodServiceLower = new TrapezoidalMemFunc("GoodServiceLower", 1.5,3.5, 4.5,6.5, true, false);
        List<MemFunc> goodService = new ArrayList<MemFunc>();
        goodService.add(GoodServiceUpper);goodService.add(GoodServiceLower);

        MemFunc ExcellentServiceUpper = new TrapezoidalMemFunc("ExcellentServiceUpper", 5,7,9,9, true, true);
        MemFunc ExcellentServiceLower = new TrapezoidalMemFunc("ExcellentServiceLower", 5.5, 7.5, 9,9, true, false);
        List<MemFunc> excellentService = new ArrayList<MemFunc>();
        excellentService.add(ExcellentServiceUpper);excellentService.add(ExcellentServiceLower);


        MemFunc CheapTipUpper = new TrapezoidalMemFunc("CheapTipUpper", 0,6,6,12, true, true);
        MemFunc CheapTipLower = new TrapezoidalMemFunc("CheapTipLower", 2,6,6,10, true, false);
        List<MemFunc> cheapTip = new ArrayList<MemFunc>();
        cheapTip.add(CheapTipUpper);cheapTip.add(CheapTipLower);

        MemFunc AverageTipUpper = new TrapezoidalMemFunc("AverageTipUpper", 10, 15,15,20, true, true);
        MemFunc AverageTipLower = new TrapezoidalMemFunc("AverageTipLower", 12,15,15,18, true, false);
        List<MemFunc> averageTip = new ArrayList<MemFunc>();
        averageTip.add(AverageTipUpper);averageTip.add(AverageTipLower);

        MemFunc GenerousTipUpper = new TrapezoidalMemFunc("GenerousTipUpper", 18,24,24,30, true, true);
        MemFunc GenerousTipLower = new TrapezoidalMemFunc("GenerousTipLower",20,24,24,28, true, false);
        List<MemFunc> generousTip = new ArrayList<MemFunc>();
        generousTip.add(GenerousTipUpper);generousTip.add(GenerousTipLower);

        List<Rule> ruleList = new ArrayList<Rule>();


        FuzzySet inputX = new FuzzySetImpl(0);
        FuzzySet inputY = new FuzzySetImpl(0);

        Rule rule = new Rule("1");

        rule.addAntecedent(new AntecedentOld(inputX, rancid));
        rule.addAntecedent(new AntecedentOld(inputY, poorService));
        rule.addConsequent(new Consequent(cheapTip));
        ruleList.add(rule);

        rule = new Rule("2");

        rule.addAntecedent(new AntecedentOld(inputX, rancid));
        rule.addAntecedent(new AntecedentOld(inputY, goodService));
        rule.addConsequent(new Consequent(cheapTip));
        ruleList.add(rule);

        rule = new Rule("3");

        rule.addAntecedent(new AntecedentOld(inputX, rancid));
        rule.addAntecedent(new AntecedentOld(inputY, excellentService));
        rule.addConsequent(new Consequent(averageTip));
        ruleList.add(rule);

        rule = new Rule("4");

        rule.addAntecedent(new AntecedentOld(inputX, delicious));
        rule.addAntecedent(new AntecedentOld(inputY, poorService));
        rule.addConsequent(new Consequent(averageTip));
        ruleList.add(rule);

        rule = new Rule("5");

        rule.addAntecedent(new AntecedentOld(inputX, delicious));
        rule.addAntecedent(new AntecedentOld(inputY, goodService));
        rule.addConsequent(new Consequent(averageTip));
        ruleList.add(rule);

        rule = new Rule("6");

        rule.addAntecedent(new AntecedentOld(inputX, delicious));
        rule.addAntecedent(new AntecedentOld(inputY, excellentService));
        rule.addConsequent(new Consequent(generousTip));
        ruleList.add(rule);

        double x = 4.0;
        double y = 8.5;

        System.out.println("Food Quality : "+x);
        System.out.println("Service Level : "+y);
        System.out.println(findTip(ruleList, x, y));

        x = 9.0;
        y = 6.0;

        System.out.println("Food Quality : "+x);
        System.out.println("Service Level : "+y);
        System.out.println(findTip(ruleList, x, y));

        x = 4.0;
        y = 2.5;

        System.out.println("Food Quality : "+x);
        System.out.println("Service Level : "+y);
        System.out.println(findTip(ruleList, x, y));
    }

    private static double findTip(List<Rule> ruleList, double x, double y){
        List<Double> inputs = new ArrayList<Double>();
        inputs.add(x);
        inputs.add(y);
        for(Rule rule : ruleList){
            rule.calculateFiringLevels(inputs, new ProductTnorm());
        }

        TypeReducer reducer = new TypeReducer(ruleList);

        return (reducer.ylk() + reducer.yrk())/2;
    }
}
