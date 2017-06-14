package com.ravi.fuzzyToolBox.TypeReduction;

import com.ravi.fuzzyToolBox.Rules.Rule;

import java.util.*;

/**
 * Created by 611445924 on 08/06/2017.
 */
public class TypeReducer {
    private Map<Double, Rule> ruleMapy;
    private Map<Double, Rule> ruleMapx;
    private Map<Double, Integer> ruleMapOrderx;
    private Map<Double, Integer> ruleMapOrdery;
    private List<Double> yrk;
    private List<Double> ylk;

    public TypeReducer(List<Rule> ruleList){
        ruleMapx = new HashMap<Double, Rule>();
        ruleMapy = new HashMap<Double, Rule>();

        ruleMapOrderx = new HashMap<Double, Integer>();
        ruleMapOrdery = new HashMap<Double, Integer>();

        yrk = new ArrayList<Double>();
        ylk = new ArrayList<Double>();

        double addValue = 0.0000000001;
        int count = 0;
        for(Rule rule : ruleList){
            //System.out.println((rule.getClAvg()+(addValue*count)) + " "+ (rule.getCrAvg()+(addValue*count)) + ", is "+rule.getLowerFiringLevel() + ","+rule.getUpperFiringLevel());
            if(rule.getLowerFiringLevel() > 0 || rule.getUpperFiringLevel() > 0) {
                ruleMapx.put(rule.getClAvg() + addValue * count, rule);
                ruleMapy.put(rule.getCrAvg() + addValue * count, rule);
                ruleMapOrderx.put(rule.getClAvg() + addValue * count, count);
                ruleMapOrdery.put(rule.getCrAvg() + addValue * count, count);

                count++;
            }

        }

        yrk.addAll(ruleMapy.keySet());
        Collections.sort(yrk);

        ylk.addAll(ruleMapx.keySet());
        Collections.sort(ylk);
    }

    public TypeReducer(Map<Double, Rule> ruleMapy, Map<Double, Rule> ruleMapx, List<Double> yrk, List<Double> ylk) {
        this.ruleMapy = ruleMapy;
        this.ruleMapx = ruleMapx;
        this.yrk = yrk;
        this.ylk = ylk;
    }

    public int ylR(){
        double ylkQuote = step1(ylk, ruleMapx);

        int R = 0;
        boolean test = true;
        while(test) {
            R = findR(ylk, ylkQuote);

            double ylkDQuote = step3l(ylk, ruleMapx, R);

            if(Math.abs(ylkQuote - ylkDQuote) < 0.000000001){
                test = false;
                break;
            }else{
                ylkQuote = ylkDQuote;
            }
        }

        /*if(ylk.size() >= R){
            return ruleMapOrderx.get(ylk.get(ylk.size()-1));
        }else {
            return ruleMapOrderx.get(ylk.get(R));
        }*/

        return R;
    }


    public double ylk(){
        double ylkQuote = step1(ylk, ruleMapx);

        boolean test = true;
        while(test) {
            int R = findR(ylk, ylkQuote);

            double ylkDQuote = step3l(ylk, ruleMapx, R);

            if(Math.abs(ylkQuote - ylkDQuote) < 0.000000001){
                test = false;
                break;
            }else{
                ylkQuote = ylkDQuote;
            }
        }

        return ylkQuote;
    }

    public int yrR(){
        double yrkQuote = step1(yrk, ruleMapy);

        int R =0;
        boolean test = true;
        while(test) {
            R = findR(yrk, yrkQuote);

            double yrkDQuote = step3(yrk, ruleMapy, R);

            if(Math.abs(yrkQuote -yrkDQuote)<0.000000001){
                test = false;
                break;
            }else{
                yrkQuote = yrkDQuote;
            }
        }

        /*if(yrk.size() >= R){
            return ruleMapOrdery.get(yrk.get(yrk.size()-1));
        }else {
            return ruleMapOrdery.get(yrk.get(R));
        }*/

        return R;
    }

    public double yrk(){
        double yrkQuote = step1(yrk, ruleMapy);

        boolean test = true;
        while(test) {
            int R = findR(yrk, yrkQuote);

            double yrkDQuote = step3(yrk, ruleMapy, R);

            if(Math.abs(yrkQuote -yrkDQuote)<0.000000001){
                test = false;
                break;
            }else{
                yrkQuote = yrkDQuote;
            }
        }

        return yrkQuote;
    }

    public double  step3(List<Double> yrk, Map<Double, Rule> ruleMap, int R){
        double num=0.0;
        double den=0.0;

        int count=1;
        for(double yi:yrk){
            if(yi == 0){
                continue;
            }
            Rule ruleRow = ruleMap.get(yi);

            double fri = 0.0;
            if(count<=R){
                fri = ruleRow.getLowerFiringLevel();
            }else{
                fri = ruleRow.getUpperFiringLevel();
            }

            num = num + fri * yi;
            den = den + fri;

            count++;
        }

        if(den != 0) {
            return num / den;
        }else{
            return 0.0;
        }
    }

    public double step3l(List<Double> ylk, Map<Double, Rule> ruleMap, int R){
        double num=0.0;
        double den=0.0;

        int count=1;
        for(double yi:ylk){
            if(yi == 0){
                continue;
            }
            Rule ruleRow = ruleMap.get(yi);

            double fli = 0.0;
            if(count<=R){
                fli = ruleRow.getUpperFiringLevel();
            }else{
                fli = ruleRow.getLowerFiringLevel();
            }

            num = num + fli * yi;
            den = den + fli;

            count++;
        }

        if(den != 0) {
            return num / den;
        }else{
            return 0.0;
        }
    }


    public double step1(List<Double> yrk, Map<Double, Rule> ruleMap){
        double num = 0.0;
        double den = 0.0;

        for(double yi:yrk){
            if(yi == 0){
                continue;
            }
            Rule ruleRow = ruleMap.get(yi);
            double fri = (ruleRow.getLowerFiringLevel() + ruleRow.getUpperFiringLevel())/2;
            double yrki = yi;

            num = num + fri * yrki;
            den = den + fri;
        }
        if(den != 0 ) {
            return num / den;
        }else{
            return 0.0;
        }
    }

    public int findR(List<Double> yk, double yrkQuote){
        if(yk.size() == 1){
            return 1;
        }

        int R=1;
        for(double yi:yk){
            if(yk.size() == R){
                break;
            }
            if(yi<=yrkQuote && yk.get(R)>=yrkQuote){
                break;
            }
            R++;
        }

        return R;
    }
}

