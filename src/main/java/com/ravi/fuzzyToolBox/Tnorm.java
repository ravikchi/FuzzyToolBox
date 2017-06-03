package com.ravi.fuzzyToolBox;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class Tnorm implements FZOperation {
    public List<Double> run(FuzzySet input, MemFunc memFunc, double x){
        List<Double> value = new ArrayList<Double>();
        if(input.getLSupport() == input.getRSupport()){
            value.add(memFunc.getMemGrade(x));
        }else{
            double maxGrade = 0.0;
            for (double i = Math.min(memFunc.getLSupport(), input.getLSupport(x)); i < Math.max(memFunc.getRSupport(), input.getRSupport(x)) ; i++) {
                double grade = Math.min(memFunc.getMemGrade(i), input.getMembershipFunction(x).getMemGrade(i));
                if(grade > maxGrade){
                    maxGrade = grade;
                }
            }

            value.add(maxGrade);
        }

        return value;
    }
}
