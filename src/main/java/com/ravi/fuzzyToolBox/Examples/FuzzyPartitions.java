package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.Rules.AntecedentOld;
import com.ravi.fuzzyToolBox.Rules.Consequent;
import com.ravi.fuzzyToolBox.Rules.Rule;
import com.ravi.fuzzyToolBox.Rules.Rules;
import com.ravi.fuzzyToolBox.TypeReduction.TypeReducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 24/05/2017.
 */
public class FuzzyPartitions {

    private int[][] counts;
    private int[][] upperNoveltyCounts;
    private int[][] lowerNoveltyCounts;

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

    public int[][] getUpperNoveltyCounts() {
        return upperNoveltyCounts;
    }

    public void setUpperNoveltyCounts(int[][] upperNoveltyCounts) {
        this.upperNoveltyCounts = upperNoveltyCounts;
    }

    public int[][] getLowerNoveltyCounts() {
        return lowerNoveltyCounts;
    }

    public void setLowerNoveltyCounts(int[][] lowerNoveltyCounts) {
        this.lowerNoveltyCounts = lowerNoveltyCounts;
    }

    public void initCounts() {
        int columns = 50;
        int rows = 50;

        this.counts = new int[rows][columns];
        this.upperNoveltyCounts = new int[rows][columns];
        this.lowerNoveltyCounts = new int[rows][columns];
        List<Double> inputs = null;
        for(int i=0; i<counts.length; i++){
            for(int j=0; j<counts[i].length; j++) {
                //System.out.println(" Input levels 1 : "+i+" input level 2 : "+j);

                double[] lowerFiringLevels = new double[rules.size()];
                double[] upperFiringLevels = new double[rules.size()];

                inputs = new ArrayList<Double>();
                inputs.add((double) i);
                inputs.add((double) j);

                List<Rule> triggeredRules = new ArrayList<Rule>();

                for (int k = 0; k < rules.size(); k++) {
                    Rule rule = rules.getRule(k);
                    rule.calculateFiringLevels(inputs, fzOperation);

                    if(!rule.isType2()) {
                        int rlCount = counts[i][j];
                        rlCount++;

                        counts[i][j] = rlCount;
                    }else if(rule.getUpperFiringLevel() > 0 || rule.getLowerFiringLevel() > 0) {
                        int rlCount = counts[i][j];
                        rlCount++;

                        counts[i][j] = rlCount;

                        lowerFiringLevels[k] = rule.getLowerFiringLevel();
                        upperFiringLevels[k] = rule.getUpperFiringLevel();
                        triggeredRules.add(rule);
                    }

                    /*System.out.println(rule.getName());
                    System.out.println(rule.getUpperFiringLevel());
                    System.out.println(rule.getLowerFiringLevel());*/
                }

                if(i==11 && j == 40){
                    System.out.println("test");
                }

                TypeReducer typeReducer = new TypeReducer(rules.getRules());

                /*lowerNoveltyCounts[i][j]=typeReducer.ylR();
                upperNoveltyCounts[i][j]=typeReducer.yrR();*/

                double minYl = Double.MAX_VALUE;
                double minYr = Double.MAX_VALUE;
                int l = 0;
                int r = 0;
                for (int k = 0; k <= triggeredRules.size(); k++) {
                    double ylN = 0.0;
                    double ylD = 0.0;

                    double yrN = 0.0;
                    double yrD = 0.0;
                    for (int t = 0; t < triggeredRules.size(); t++) {
                        if(t<k) {
                            ylN = ylN + triggeredRules.get(t).getCrAvg() * triggeredRules.get(t).getUpperFiringLevel();
                            ylD = ylD + triggeredRules.get(t).getUpperFiringLevel();
                        }else{
                            ylN = ylN + triggeredRules.get(t).getCrAvg() * triggeredRules.get(t).getLowerFiringLevel();
                            ylD = ylD + triggeredRules.get(t).getLowerFiringLevel();
                        }
                    }

                    for (int t = 0; t < triggeredRules.size(); t++) {
                        if(t < k) {
                            yrN = yrN + triggeredRules.get(t).getClAvg() * triggeredRules.get(t).getLowerFiringLevel();
                            yrD = yrD + triggeredRules.get(t).getLowerFiringLevel();
                        }else{
                            yrN = yrN + triggeredRules.get(t).getClAvg() * triggeredRules.get(t).getUpperFiringLevel();
                            yrD = yrD + triggeredRules.get(t).getUpperFiringLevel();
                        }
                    }

                    double yl = ylN/ylD;
                    double yr = yrN/yrD;

                    if(minYl >= yl){
                        minYl = yl;
                        l = k;
                    }

                    if(minYr >= yr){
                        minYr = yr;
                        r = k;
                    }
                }

                lowerNoveltyCounts[i][j] = l;
                upperNoveltyCounts[i][j] = r;
            }
        }

    }

    public static Rules getRules(RulesInputs inputs){

        Rules rules = new Rules();

        Rule rule = new Rule("Low x1, low x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getLow()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getLow()));
        rule.addConsequent(new Consequent(inputs.getLowConseqent()));
        rules.addRule(rule);

        rule = new Rule("Low x1, mid x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getLow()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getMid()));
        rule.addConsequent(new Consequent(inputs.getLowConseqent()));
        rules.addRule(rule);

        rule = new Rule("Low x1, high x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getLow()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getHigh()));
        rule.addConsequent(new Consequent(inputs.getLowConseqent()));
        rules.addRule(rule);


        rule = new Rule("Mid x1, low x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getMid()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getLow()));
        rule.addConsequent(new Consequent(inputs.getMidConseqent()));
        rules.addRule(rule);

        rule = new Rule("Mid x1, mid x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getMid()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getMid()));
        rule.addConsequent(new Consequent(inputs.getMidConseqent()));
        rules.addRule(rule);

        rule = new Rule("Mid x1, high x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getMid()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getHigh()));
        rule.addConsequent(new Consequent(inputs.getMidConseqent()));
        rules.addRule(rule);

        rule = new Rule("High x1, low x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getHigh()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getLow()));
        rule.addConsequent(new Consequent(inputs.getMidConseqent()));
        rules.addRule(rule);

        rule = new Rule("High x1, mid x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getHigh()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getMid()));
        rule.addConsequent(new Consequent(inputs.getHighConseqent()));
        rules.addRule(rule);

        rule = new Rule("High x1, high x2");
        rule.addAntecedent(new AntecedentOld(inputs.getX(), inputs.getHigh()));
        rule.addAntecedent(new AntecedentOld(inputs.getY(), inputs.getHigh()));
        rule.addConsequent(new Consequent(inputs.getHighConseqent()));
        rules.addRule(rule);

        return rules;
    }
}
