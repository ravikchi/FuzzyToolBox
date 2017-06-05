package com.ravi.fuzzyToolBox.MemFunctions;

/**
 * Created by 611445924 on 24/05/2017.
 */
public class TrapezoidalMemFunc implements MemFunc {
    private String name;
    private double start;
    private double top1;
    private double top2;
    private double end;

    public TrapezoidalMemFunc(String name, double start, double top1, double top2, double end) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getTop1() {
        return top1;
    }

    public void setTop1(double top1) {
        this.top1 = top1;
    }

    public double getTop2() {
        return top2;
    }

    public void setTop2(double top2) {
        this.top2 = top2;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getLSupport() {
        return start;
    }

    public double getRSupport() {
        return end;
    }
}
