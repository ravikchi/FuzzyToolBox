package com.ravi.fuzzyToolBox.MemFunctions;

/**
 * Created by 611445924 on 08/06/2017.
 */
public class GaussianMemFunc implements MemFunc {
    private double m1;
    private double m2;
    private double sigma;

    public GaussianMemFunc(double m1, double m2, double sigma) {
        this.m1 = m1;
        this.m2 = m2;
        this.sigma = sigma;
    }

    public double gaussianFunction(double m, double sigma, double x){
        double negNum = -1;
        double val = (negNum/2) * ((x-m)/sigma) * ((x-m)/sigma);
        return Math.exp(val);
    }

    @Override
    public double getMemGrade(double x) {
        if(x<m1)
            return gaussianFunction(m1,sigma,x);
        else if(x>m2)
            return gaussianFunction(m2,sigma,x);
        else if(x>=m1 && x<=m2)
            return 1.0;

        return 0.0;
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

    }

    @Override
    public double getTop1() {
        return 0;
    }

    @Override
    public void setTop1(double top1) {

    }

    @Override
    public double getTop2() {
        return 0;
    }

    @Override
    public void setTop2(double top2) {

    }

    @Override
    public double getEnd() {
        return 0;
    }

    @Override
    public void setEnd(double end) {

    }
}
