package com.ravi.fuzzyToolBox.MemFunctions;

/**
 * Created by 611445924 on 24/05/2017.
 */
public class SingletonMemFunc implements MemFunc {

    public double getMemGrade(double x) {
        return x;
    }

    public double getLSupport() {
        return 0;
    }

    public String getName() {
        return "";
    }

    public void setName(String name) {

    }


    public double getRSupport() {
        return 0;
    }

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

    public void setStart(double start) {

    }

    public double getTop1() {
        return 0;
    }

    public void setTop1(double top1) {

    }

    public double getTop2() {
        return 0;
    }

    public void setTop2(double top2) {

    }

    public double getEnd() {
        return 0;
    }

    public void setEnd(double end) {

    }
}
