package com.ravi.fuzzyToolBox.FuzzySets;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.List;

/**
 * Created by rc16956 on 23/05/2017.
 */
public interface FuzzySet {

    public double getLSupport(double value);
    public double getRSupport(double value);
    public double getLSupport();
    public double getRSupport();
    public double getSpread();
    public void setValue(double value);
    public double getValue();
    public MemFunc getMembershipFunction(double value);
    public List<MemFunc> getMemFuncs(double value);
    public void setMemFuncs(List<MemFunc> memFuncs);
    public boolean isType2();
    public double getIncrement();
    public void setIncrement(double increment);
    public MemFunc getMembershipFunction();
    public void setMembershipFunction(MemFunc membershipFunction);
}
