package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.NoveltyPartRules;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.ProductTnorm;
import com.ravi.fuzzyToolBox.Rules.Antecedent;
import com.ravi.fuzzyToolBox.Rules.Rule;
import com.ravi.fuzzyToolBox.Rules.Rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 11/06/2017.
 */
public class FLC {
    private Rules rules;
    private List<FuzzySet> inputs;
    private FZOperation fzOperation;
    private double starti = Double.MAX_VALUE;
    private double startj = Double.MAX_VALUE;
    private double endi = Double.MIN_VALUE;
    private double endj = Double.MIN_VALUE;
    private double incrementi;
    private double incrementj;

    private int[][] counts;

    public FLC(Rules rules, List<FuzzySet> inputs, FZOperation fzOperation) {
        this.rules = rules;
        this.inputs = inputs;
        this.fzOperation = fzOperation;
    }

    public FLC(Rules rules, FZOperation fzOperation) {
        this.rules = rules;
        this.fzOperation = fzOperation;
    }

    public void initiate(int counti, int countj){
        for (int i = 0; i < rules.getRules().size(); i++) {
            Rule rule = rules.getRule(i);
            Antecedent ai = rule.getAntecedents().get(0);
            Antecedent aj = rule.getAntecedents().get(1);

            starti = Math.min(starti, Math.min(ai.getLowerMemFunction().getLSupport(), ai.getUpperMemFunction().getLSupport()));
            startj = Math.min(startj, Math.min(aj.getLowerMemFunction().getLSupport(), aj.getUpperMemFunction().getLSupport()));

            endi = Math.max(endi, Math.min(ai.getLowerMemFunction().getRSupport(), ai.getUpperMemFunction().getRSupport()));
            endj = Math.max(endj, Math.min(aj.getLowerMemFunction().getRSupport(), aj.getUpperMemFunction().getRSupport()));
        }

        incrementj = (endj-startj)/counti;
        incrementi = (endi-starti)/countj;

        counts = new int[counti][countj];
    }

    public void runRules(){
        int counti = 0;
        for(double i=starti; i<=endi; i=i+incrementi) {
            int countj = 0;
            for (double j=startj; j<=endj; j=j+incrementj) {
                BigDecimal ib = new BigDecimal(i).setScale(2,BigDecimal.ROUND_HALF_UP);
                BigDecimal jb = new BigDecimal(j).setScale(2,BigDecimal.ROUND_HALF_UP);

                List<Double> inputs = new ArrayList<Double>();
                inputs.add(ib.doubleValue());
                inputs.add(jb.doubleValue());

                List<Rule> triggeredRules = new ArrayList<Rule>();

                for (int k = 0; k < rules.getRules().size(); k++) {
                    Rule rule = rules.getRule(k);

                    rule.calculateFiringLevels(inputs, fzOperation);

                    if(rule.getUpperFiringLevel() > 0 || rule.getLowerFiringLevel() > 0){
                        triggeredRules.add(rule);
                    }
                }

                counts[counti][countj] = triggeredRules.size();
                countj++;
            }
            counti++;
        }
    }

    public int[][] getCounts() {
        return counts;
    }

    public void setCounts(int[][] counts) {
        this.counts = counts;
    }

    public static void main(String[] args) {

        NoveltyPartRules data = new NoveltyPartRules();
        Rules rules = data.getRules();

        FZOperation fzOperation = new ProductTnorm();

        FLC flc = new FLC(rules, fzOperation);
        flc.initiate(49, 49);
        flc.runRules();

        for (int i = 0; i < flc.getCounts().length; i++) {
            for (int j = 0; j < flc.getCounts()[i].length; j++) {
                System.out.print(flc.getCounts()[i][j]+"  ");
            }
            System.out.println();
        }


    }

}
