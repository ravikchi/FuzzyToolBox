package com.ravi.fuzzyToolBox.FuzzySets;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;

/**
 * Created by rc16956 on 23/05/2017.
 */
public class FuzzySetImpl implements FuzzySet {
    private double spread;
    private double value;
    private MemFunc membershipFunction;

    public MemFunc getMembershipFunction(double value) {
        return new TrapezoidalMemFunc("",getLSupport(value), value, value, getRSupport(value), false, false);
    }

    public void setMembershipFunction(MemFunc membershipFunction) {
        this.membershipFunction = membershipFunction;
    }

    public FuzzySetImpl(double spread) {
        this.spread = spread;
    }

    public double getLSupport(double value) {
        return value - spread;
    }

    public double getRSupport(double value) {
        return value + spread;
    }

    public double getLSupport() {
        return value - spread;
    }

    public double getRSupport() {
        return value + spread;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
