package com.ravi.fuzzyToolBox.MemFunctions;

/**
 * Created by 611445924 on 08/06/2017.
 */
public class GaussianMemFunc implements MemFunc {
    private double m1;
    private double m2;
    private double sigma;

    private boolean upper;

    private double start;
    private double top1;
    private double top2;
    private double end;


    public GaussianMemFunc(double m1, double m2, double sigma, boolean upper) {
        this.m1 = m1;
        this.m2 = m2;
        this.sigma = sigma;
        this.upper = upper;

        this.start = m1 - sigma * 4;
        this.end =  m2 + sigma * 4;
    }

    public double gaussianFunction(double m, double sigma, double x){
        double negNum = -1;
        double val = (negNum/2) * ((x-m)/sigma) * ((x-m)/sigma);
        return Math.exp(val);
    }

    public double upperGrade(double x){
        if(x<m1)
            return gaussianFunction(m1,sigma,x);
        else if(x>m2)
            return gaussianFunction(m2,sigma,x);
        else if(x>=m1 && x<=m2)
            return 1.0;

        return 0.0;
    }

    public double lowerGrade(double x){
        if((m1+m2)/2 >= x)
            return gaussianFunction(m2, sigma, x);
        else
            return gaussianFunction(m1, sigma, x);
    }

    @Override
    public double getMemGrade(double x) {
        if(upper){
            return upperGrade(x);
        }else{
            return lowerGrade(x);
        }
    }

    @Override
    public double getLSupport() {
        return 0;
    }

    @Override
    public double getRSupport() {
        return 0;
    }

    @Override
    public double getStart() {
        return 0;
    }

    @Override
    public boolean isUpper() {
        return false;
    }

    @Override
    public boolean isType2() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setStart(double start) {
        this.start = start;
    }

    @Override
    public double getTop1() {
        return top1;
    }

    @Override
    public void setTop1(double top1) {
        this.top1 = top1;
    }

    @Override
    public double getTop2() {
        return top2;
    }

    @Override
    public void setTop2(double top2) {
        this.top2 = top2;
    }

    @Override
    public double getEnd() {
        return end;
    }

    @Override
    public void setEnd(double end) {
        this.end = end;
    }
}
