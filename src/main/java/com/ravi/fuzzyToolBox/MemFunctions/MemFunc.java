package com.ravi.fuzzyToolBox.MemFunctions;

/**
 * Created by 611445924 on 24/05/2017.
 */
public interface MemFunc {
    public double getMemGrade(double x);
    public double getLSupport();
    public double getRSupport();
    public double getStart();

    public String getName() ;

    public void setName(String name) ;

    public void setStart(double start) ;

    public double getTop1() ;

    public void setTop1(double top1);

    public double getTop2() ;

    public void setTop2(double top2) ;

    public double getEnd() ;

    public void setEnd(double end);
}
