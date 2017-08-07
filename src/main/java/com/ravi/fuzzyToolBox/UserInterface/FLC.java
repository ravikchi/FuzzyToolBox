package com.ravi.fuzzyToolBox.UserInterface;

import com.ravi.fuzzyToolBox.Examples.FuzzyPartitions;
import com.ravi.fuzzyToolBox.Examples.NoveltyPartRules;
import com.ravi.fuzzyToolBox.Examples.RulesInputs;
import com.ravi.fuzzyToolBox.FZOperation;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;
import com.ravi.fuzzyToolBox.ProductTnorm;
import com.ravi.fuzzyToolBox.Rules.Antecedent;
import com.ravi.fuzzyToolBox.Rules.Rule;
import com.ravi.fuzzyToolBox.Rules.Rules;
import com.ravi.fuzzyToolBox.RulesComparator;
import com.ravi.fuzzyToolBox.TypeReduction.TypeReducer;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 611445924 on 11/06/2017.
 */
public class FLC {
    private Rules rules;
    private List<FuzzySet> inputs;
    private List<MemFunc> inputILowerMemFunc = new ArrayList<MemFunc>();
    private List<MemFunc> inputIUpperMemFunc = new ArrayList<MemFunc>();
    private List<MemFunc> inputJLowerMemFunc = new ArrayList<MemFunc>();
    private List<MemFunc> inputJUpperMemFunc = new ArrayList<MemFunc>();
    private FZOperation fzOperation;
    private double starti = Double.MAX_VALUE;
    private double startj = Double.MAX_VALUE;
    private double endi = Double.MIN_VALUE;
    private double endj = Double.MIN_VALUE;
    private double incrementi;
    private double incrementj;

    private int[][] counts;

    private int[][] upperNoveltyCounts;
    private int[][] lowerNoveltyCounts;

    public FLC(Rules rules, List<FuzzySet> inputs, FZOperation fzOperation) {
        this.rules = rules;
        this.inputs = inputs;
        this.fzOperation = fzOperation;
    }

    public FLC(Rules rules, FZOperation fzOperation) {
        this.rules = rules;
        this.fzOperation = fzOperation;
    }
    public List<MemFunc> getInputIUpperMemFunc() {
        return inputIUpperMemFunc;
    }

    public double getStarti() {
        return starti;
    }

    public double getStartj() {
        return startj;
    }

    public double getEndi() {
        return endi;
    }

    public double getEndj() {
        return endj;
    }

    public double getIncrementi() {
        return incrementi;
    }

    public double getIncrementj() {
        return incrementj;
    }

    public void setInputIUpperMemFunc(List<MemFunc> inputIUpperMemFunc) {
        this.inputIUpperMemFunc = inputIUpperMemFunc;
    }

    public List<MemFunc> getInputJLowerMemFunc() {
        return inputJLowerMemFunc;
    }

    public void setInputJLowerMemFunc(List<MemFunc> inputJLowerMemFunc) {
        this.inputJLowerMemFunc = inputJLowerMemFunc;
    }

    public List<MemFunc> getInputJUpperMemFunc() {
        return inputJUpperMemFunc;
    }

    public void setInputJUpperMemFunc(List<MemFunc> inputJUpperMemFunc) {
        this.inputJUpperMemFunc = inputJUpperMemFunc;
    }

    public List<MemFunc> getInputILowerMemFunc() {
        return inputILowerMemFunc;
    }

    public void setInputILowerMemFunc(List<MemFunc> inputILowerMemFunc) {
        this.inputILowerMemFunc = inputILowerMemFunc;
    }

    public Set<Double> calculateRulePartitionsi(){
        Set<Double> switches = new HashSet<Double>();
        for(double i=starti; i<=endi; i=i+incrementi) {

            for (int j = 0; j<inputILowerMemFunc.size(); j++) {
                MemFunc lower = inputILowerMemFunc.get(j);
                MemFunc upper = inputIUpperMemFunc.get(j);

                if(lower.getStart() >= i-incrementi && lower.getStart() <= i+incrementi){
                    switches.add(lower.getStart());
                }


                if(lower.getEnd() >= i-incrementi && lower.getEnd() <= i+incrementi){
                    switches.add(lower.getEnd());
                }

                if(upper.getStart() >= i-incrementi && upper.getStart() <= i+incrementi){
                    switches.add(upper.getStart());
                }

                if(upper.getEnd() >= i-incrementi && upper.getEnd() <= i+incrementi){
                    switches.add(upper.getEnd());
                }
            }

        }

        return switches;
    }

    public Set<Double> calculateRulePartitionsj(){
        Set<Double> switches = new HashSet<Double>();
        for(double i=starti; i<=endi; i=i+incrementi) {

            for (int j = 0; j<inputJLowerMemFunc.size(); j++) {
                MemFunc lower = inputJLowerMemFunc.get(j);
                MemFunc upper = inputJUpperMemFunc.get(j);

                if(lower.getStart() >= i-incrementi && lower.getStart() <= i+incrementi){
                    switches.add(lower.getStart());
                }


                if(lower.getEnd() >= i-incrementi && lower.getEnd() <= i+incrementi){
                    switches.add(lower.getEnd());
                }

                if(upper.getStart() >= i-incrementi && upper.getStart() <= i+incrementi){
                    switches.add(upper.getStart());
                }

                if(upper.getEnd() >= i-incrementi && upper.getEnd() <= i+incrementi){
                    switches.add(upper.getEnd());
                }
            }

        }

        return switches;
    }

    public void initiate(int counti, int countj){
        for (int i = 0; i < rules.getRules().size(); i++) {
            Rule rule = rules.getRule(i);
            Antecedent ai = rule.getAntecedents().get(0);
            Antecedent aj = rule.getAntecedents().get(1);

            if(ai.isType2() && aj.isType2()) {

                starti = Math.min(starti, Math.min(ai.getLowerMemFunction().getLSupport(), ai.getUpperMemFunction().getLSupport()));
                startj = Math.min(startj, Math.min(aj.getLowerMemFunction().getLSupport(), aj.getUpperMemFunction().getLSupport()));

                endi = Math.max(endi, Math.min(ai.getLowerMemFunction().getRSupport(), ai.getUpperMemFunction().getRSupport()));
                endj = Math.max(endj, Math.min(aj.getLowerMemFunction().getRSupport(), aj.getUpperMemFunction().getRSupport()));

                if (!inputILowerMemFunc.contains(ai.getLowerMemFunction())) {
                    inputILowerMemFunc.add(ai.getLowerMemFunction());
                }

                if (!inputIUpperMemFunc.contains(ai.getUpperMemFunction())) {
                    inputIUpperMemFunc.add(ai.getUpperMemFunction());
                }

                if (!inputJLowerMemFunc.contains(aj.getLowerMemFunction())) {
                    inputJLowerMemFunc.add(aj.getLowerMemFunction());
                }

                if (!inputJUpperMemFunc.contains(aj.getUpperMemFunction())) {
                    inputJUpperMemFunc.add(aj.getUpperMemFunction());
                }
            }else{
                starti = Math.min(starti, Math.min(ai.getMemFuncion().getLSupport(), ai.getMemFuncion().getLSupport()));
                startj = Math.min(startj, Math.min(aj.getMemFuncion().getLSupport(), aj.getMemFuncion().getLSupport()));

                endi = Math.max(endi, Math.min(ai.getMemFuncion().getRSupport(), ai.getMemFuncion().getRSupport()));
                endj = Math.max(endj, Math.min(aj.getMemFuncion().getRSupport(), aj.getMemFuncion().getRSupport()));

                if (!inputIUpperMemFunc.contains(ai.getMemFuncion())) {
                    inputIUpperMemFunc.add(ai.getMemFuncion());
                }
                if (!inputJUpperMemFunc.contains(aj.getMemFuncion())) {
                    inputJUpperMemFunc.add(aj.getMemFuncion());
                }
            }
        }

        incrementj = (endj-startj)/(counti-1);
        incrementi = (endi-starti)/(countj-1);

        counts = new int[counti][countj];
        lowerNoveltyCounts = new int[counti][countj];
        upperNoveltyCounts = new int[counti][countj];
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
                calculateNoveltyPartitions(triggeredRules, counti, countj);
                //calcNovPartitionsTR(rules.getRules(), counti, countj);
                countj++;
            }
            counti++;
        }
    }

    public void runRules(List<FuzzySet> inputs){
        int counti = 0;
        for(double i=starti; i<endi; i=i+incrementi) {
            int countj = 0;
            for (double j=startj; j<endj; j=j+incrementj) {
                BigDecimal ib = new BigDecimal(i).setScale(2,BigDecimal.ROUND_HALF_UP);
                BigDecimal jb = new BigDecimal(j).setScale(2,BigDecimal.ROUND_HALF_UP);
                if(jb.doubleValue() > 11){
                    System.out.println();
                }

                inputs.get(0).setValue(ib.doubleValue());
                inputs.get(1).setValue(jb.doubleValue());

                List<Rule> triggeredRules = new ArrayList<Rule>();

                for (int k = 0; k < rules.getRules().size(); k++) {
                    Rule rule = rules.getRule(k);

                    rule.calculateFiringLevels(fzOperation, inputs);

                    if(rule.getUpperFiringLevel() > 0 || rule.getLowerFiringLevel() > 0 || rule.getFiringLevel() > 0){
                        triggeredRules.add(rule);
                    }
                }

                counts[counti][countj] = triggeredRules.size();
                calculateNoveltyPartitions(triggeredRules, counti, countj);
                //calcNovPartitionsTR(rules.getRules(), counti, countj);
                countj++;
            }
            counti++;
        }
    }

    private void calcNovPartitionsTR(List<Rule> allRules, int counti, int countj){
        TypeReducer reducer = new TypeReducer(allRules);
        //System.out.println(reducer.ylk());
        //System.out.println(reducer.yrk());
        lowerNoveltyCounts[counti][countj] = reducer.ylR();
        upperNoveltyCounts[counti][countj] = reducer.yrR();
    }

    private void calculateNoveltyPartitions(List<Rule> triggeredRules, int counti, int countj){
        Collections.sort(triggeredRules, new RulesComparator());

        double minYl = 0.0;
        double maxYr = 0.0;
        int l = 0;
        int r = 0;
        for (int k = 0; k <= triggeredRules.size(); k++) {
            double ylN = 0.0;
            double ylD = 0.0;

            double yrN = 0.0;
            double yrD = 0.0;
            for (int t = 0; t < triggeredRules.size(); t++) {
                if(t<k) {
                    ylN = ylN + triggeredRules.get(t).getClAvg() * triggeredRules.get(t).getUpperFiringLevel();
                    ylD = ylD + triggeredRules.get(t).getUpperFiringLevel();
                    //System.out.print(t + " upper + ");
                }else{
                    ylN = ylN + triggeredRules.get(t).getClAvg() * triggeredRules.get(t).getLowerFiringLevel();
                    ylD = ylD + triggeredRules.get(t).getLowerFiringLevel();
                    //System.out.print(t + " lower + ");
                }
            }

            //System.out.println();

            for (int t = 0; t < triggeredRules.size(); t++) {
                if(t<k) {
                    yrN = yrN + triggeredRules.get(t).getCrAvg() * triggeredRules.get(t).getLowerFiringLevel();
                    yrD = yrD + triggeredRules.get(t).getLowerFiringLevel();
                    //System.out.print(" yi "+ triggeredRules.get(t).getCrAvg()+" lower "+ triggeredRules.get(t).getLowerFiringLevel()+" + ");
                }else{
                    yrN = yrN + triggeredRules.get(t).getCrAvg() * triggeredRules.get(t).getUpperFiringLevel();
                    yrD = yrD + triggeredRules.get(t).getUpperFiringLevel();
                    //System.out.print(" yi "+ triggeredRules.get(t).getCrAvg()+" upper "+ triggeredRules.get(t).getUpperFiringLevel()+" + ");
                }
            }

            //System.out.println();

            double yl = ylN/ylD;
            double yr = yrN/yrD;

            if(k==0){
                minYl = yl;
                maxYr = yr;
                l = k;
                r = k;
            }

            if(minYl >= yl){
                minYl = yl;
                l = k;
            }

            if(maxYr <= yr){
                maxYr = yr;
                r = k;
            }
        }

        lowerNoveltyCounts[counti][countj] = l;
        upperNoveltyCounts[counti][countj] = r;
    }

    public int[][] getUpperNoveltyCounts() {
        return upperNoveltyCounts;
    }

    public int[][] getLowerNoveltyCounts() {
        return lowerNoveltyCounts;
    }

    public int[][] getCounts() {
        return counts;
    }

    public void setCounts(int[][] counts) {
        this.counts = counts;
    }

    public static void main(String[] args) {

        NoveltyPartRules data = new NoveltyPartRules();
        Rules rules = data.getRules("3");

        FZOperation fzOperation = new ProductTnorm();


        List<FuzzySet> inputs = new ArrayList<FuzzySet>();

        inputs.add(new FuzzySetImpl(1,1/10, false));
        inputs.add(new FuzzySetImpl(1, 1/10, false));

        RulesInputs rulesInputs = new RulesInputs(inputs.get(0), inputs.get(1), 11);
        List<MemFunc> low = new ArrayList<MemFunc>();
        MemFunc lowMem = new TrapezoidalMemFunc("lowLower",0, 0, 9, 19, true, false);
        MemFunc lowLower = new TrapezoidalMemFunc("lowUpper",0, 0, 11, 21, true, true);
        low.add(lowMem);
        low.add(lowLower);

        List<MemFunc> lowC = new ArrayList<MemFunc>();
        List<MemFunc> midC = new ArrayList<MemFunc>();
        List<MemFunc> highC = new ArrayList<MemFunc>();

        MemFunc lowLowerConsequent = new TrapezoidalMemFunc("lowConsequent", 0, 0, 9, 19, true, false);
        MemFunc lowUpperConsequent = new TrapezoidalMemFunc("lowUpperConsequent",0, 0, 11, 21, true, true);
        lowC.add(lowLowerConsequent);
        lowC.add(lowUpperConsequent);

        List<MemFunc> mid = new ArrayList<MemFunc>();
        MemFunc midMem = new TrapezoidalMemFunc("midUpper",10, 20, 31, 41, true, true);
        MemFunc midLower = new TrapezoidalMemFunc("midLower",12, 22, 29, 39, true, false);
        mid.add(midMem);
        mid.add(midLower);

        MemFunc midLowerConsequent = new TrapezoidalMemFunc("midConsequent", 12, 22, 29, 39, true, false);
        MemFunc midUpperConsequent = new TrapezoidalMemFunc("midUpperConsequent",10, 20, 31, 41, true, true);
        midC.add(midLowerConsequent);
        midC.add(midUpperConsequent);

        List<MemFunc> high = new ArrayList<MemFunc>();
        MemFunc highMem = new TrapezoidalMemFunc("highUpper",30, 40, 50, 50, true, true);
        MemFunc highLower = new TrapezoidalMemFunc("highLower",32, 42, 50, 50, true, false);
        high.add(highMem);
        high.add(highLower);

        MemFunc highMemConsequent = new TrapezoidalMemFunc("highUpperConsequent",30, 40, 50, 50, true, true);
        MemFunc highLowerConsequent = new TrapezoidalMemFunc("highLowerConsequent",32, 42, 50, 50, true, false);
        highC.add(highMemConsequent);
        highC.add(highLowerConsequent);

        rulesInputs.setLow(low);
        rulesInputs.setMid(mid);
        rulesInputs.setHigh(high);

        rulesInputs.setLowConseqent(lowC);
        rulesInputs.setMidConseqent(midC);
        rulesInputs.setHighConseqent(highC);

        FLC flc = new FLC(FuzzyPartitions.getRules(rulesInputs), fzOperation);
        flc.initiate(149, 149);
        flc.runRules(inputs);

        System.out.println("########################################################################################################################################################################################");

        for (int i = 0; i < flc.getCounts().length-1; i++) {
            for (int j = 0; j < flc.getCounts()[i].length-1; j++) {
                System.out.print(flc.getCounts()[i][j]+" ");
            }
            System.out.println();
        }

//        System.out.println("########################################################################################################################################################################################");
//
//        for (int i = 0; i < flc.getLowerNoveltyCounts().length-1; i++) {
//            for (int j = 0; j < flc.getLowerNoveltyCounts()[i].length-1; j++) {
//                System.out.print(flc.getLowerNoveltyCounts()[i][j]+" ");
//            }
//            System.out.println();
//        }
//
//        System.out.println("########################################################################################################################################################################################");
//
//        for (int i = 0; i < flc.getUpperNoveltyCounts().length-1; i++) {
//            for (int j = 0; j < flc.getUpperNoveltyCounts()[i].length-1; j++) {
//                System.out.print(flc.getUpperNoveltyCounts()[i][j]+" ");
//            }
//            System.out.println();
//        }


    }

}
