package com.ravi.fuzzyToolBox.MemFunctions;

/**
 * Created by rc16956 on 09/06/2017.
 */
public class PWLMF implements MemFunc{
    private String name;
    private double start;
    private double top1;
    private double top2;
    private double end;
    private boolean type2;
    private boolean upper;

    private double y1;
    private double y2;

    public PWLMF(String name, double start, double top1, double top2, double end, boolean type2, boolean upper, double y1, double y2) {
        this.name = name;
        this.start = start;
        this.top1 = top1;
        this.top2 = top2;
        this.end = end;
        this.type2 = type2;
        this.upper = upper;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public boolean isType2() {
        return type2;
    }

    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    @Override
    public boolean isUpper() {
        return upper;
    }

    public void setUpper(boolean upper) {
        this.upper = upper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getMemGrade(double x) {
        if(start > x || end < x ){
            return y1;
        }

        double value = 0.0;
        if(top1<= x && x <= top2){
            value = y2;
        }else if(x < top1) {
            value = (y2-y1) * (x - start) / (top1 - start) + y1;
        }else if(x > top2){
            value = (y2-y1) * (end - x)/(end - top2) + y2;
        }

        return value;
    }

    @Override
    public double getLSupport() {
        return start;
    }

    @Override
    public double getRSupport() {
        return end;
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
}
