package com.ravi.fuzzyToolBox.FuzzySets;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

/**
 * Created by rc16956 on 23/05/2017.
 */
public interface FuzzySet {

    public double getLSupport(double value);
    public double getRSupport(double value);
    public double getLSupport();
    public double getRSupport();
    public void setValue(double value);
    public MemFunc getMembershipFunction(double value);
    public void setMembershipFunction(MemFunc membershipFunction);
}
