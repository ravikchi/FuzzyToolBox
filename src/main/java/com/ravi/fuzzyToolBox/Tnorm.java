package com.ravi.fuzzyToolBox;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class Tnorm implements FZOperation {
    public double run(FuzzySet input, MemFunc memFunc, double x){
        double value = 0.0;
        if(input.getLSupport() == input.getRSupport()){
            value = memFunc.getMemGrade(x);
        }else{
            double maxGrade = 0.0;
            for (double i = Math.min(memFunc.getLSupport(), input.getLSupport(x)); i < Math.max(memFunc.getRSupport(), input.getRSupport(x)) ; i++) {
                double grade = Math.min(memFunc.getMemGrade(i), input.getMembershipFunction(x).getMemGrade(i));
                if(grade > maxGrade){
                    maxGrade = grade;
                }
            }

            value = maxGrade;
        }

        return value;
    }
}
