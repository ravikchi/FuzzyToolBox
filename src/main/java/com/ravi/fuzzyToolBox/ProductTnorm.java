package com.ravi.fuzzyToolBox;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

/**
 * Created by 611445924 on 08/06/2017.
 */
public class ProductTnorm implements FZOperation {
    @Override
    public double run(FuzzySet input, MemFunc memFunc, double x) {
        double value = 0.0;
        if(input.getLSupport() == input.getRSupport()){
            value = memFunc.getMemGrade(x);
        }else if(input.isType2()){
            double upperFiringLevel = 0.0;
            double lowerFiringLevel = 0.0;
            MemFunc upper = null;
            MemFunc lower = null;
            for (MemFunc memFunc1:input.getMemFuncs(x)) {
                if(memFunc1.isUpper()){
                    upper = memFunc1;
                }else{
                    lower = memFunc1;
                }
            }

            double increment = (upper.getEnd()-upper.getStart())*2/100;

            for (double i = upper.getStart(); i <= upper.getEnd() ; i=i+increment) {
                double grade = memFunc.getMemGrade(i) * upper.getMemGrade(i);
                if(grade > upperFiringLevel){
                    upperFiringLevel = grade;
                }
            }

            for (double i = lower.getStart(); i <= lower.getEnd() ; i=i+increment) {
                double grade = memFunc.getMemGrade(i) * lower.getMemGrade(i);
                if(grade > lowerFiringLevel){
                    lowerFiringLevel = grade;
                }
            }

            value = (upperFiringLevel + lowerFiringLevel)/2;
        }else{
            double maxGrade = 0.0;
            for (double i = input.getLSupport(x); i <= input.getRSupport(x) ; i=i+input.getIncrement()) {
                double grade = memFunc.getMemGrade(i) * input.getMembershipFunction(x).getMemGrade(i);
                if(grade > maxGrade){
                    maxGrade = grade;
                }
            }

            value = maxGrade;
        }

        return value;
    }

    @Override
    public double run(MemFunc memFunc, MemFunc memFunc1, double x) {
        return memFunc.getMemGrade(x) * memFunc1.getMemGrade(x);

    }

    @Override
    public double run(FuzzySet input, MemFunc memFunc) {
        double value = memFunc.getMemGrade(input.getValue());

        if(input.isType2()){
            double upperFiringLevel = 0.0;
            double lowerFiringLevel = 0.0;
            MemFunc upper = null;
            MemFunc lower = null;
            for (MemFunc memFunc1:input.getMemFuncs(input.getValue())) {
                if(memFunc1.isUpper()){
                    upper = memFunc1;
                }else{
                    lower = memFunc1;
                }
            }

            double increment = (upper.getEnd()-upper.getStart())*2/100;

            for (double i = upper.getStart(); i <= upper.getEnd() ; i=i+increment) {
                double grade = memFunc.getMemGrade(i) * upper.getMemGrade(i);
                if(grade > upperFiringLevel){
                    upperFiringLevel = grade;
                }
            }

            for (double i = lower.getStart(); i <= lower.getEnd() ; i=i+increment) {
                double grade = memFunc.getMemGrade(i) * lower.getMemGrade(i);
                if(grade > lowerFiringLevel){
                    lowerFiringLevel = grade;
                }
            }

            value = (upperFiringLevel + lowerFiringLevel)/2;
        }else if(input.getLSupport() != input.getRSupport()){
            for (double i = input.getLSupport(); i <= input.getRSupport() ; i=i+input.getIncrement()) {
                double grade = memFunc.getMemGrade(i) * input.getMembershipFunction().getMemGrade(i);
                if (grade > value) {
                    value = grade;
                }
            }
        }

        return value;
    }

    @Override
    public double operation(double x, double y) {
        return x * y;
    }
}
