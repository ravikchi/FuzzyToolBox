package com.ravi.fuzzyToolBox.MemFunctions;

/**
 * Created by 611445924 on 24/05/2017.
 */
public class TrapezoidalMemFunc implements MemFunc {
    private double start;
    private double top1;
    private double top2;
    private double end;

    public TrapezoidalMemFunc(double start, double top1, double top2, double end) {
        this.start = start;
        this.top1 = top1;
        this.top2 = top2;
        this.end = end;
    }

    public double getMemGrade(double x) {
        double value = 0.0;
        if(start >= x || end <= x ){
            value = 0.0;
        }else if(x < top1) {
            value = (x-start)/(top1 -start);
        }else if(top1<= x && x <= top2){
            value = 1.0;
        }else if(x > top2){
            value = (end - x)/(end - top2);
        }

        return value;
    }

    public double getLSupport() {
        return start;
    }

    public double getRSupport() {
        return end;
    }
}
