package com.ravi.fuzzyToolBox.FuzzySets;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.PWLMF;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;

/**
 * Created by rc16956 on 23/05/2017.
 */
public class FuzzySetImpl implements FuzzySet {
    private double spread;
    private double value;
    private MemFunc membershipFunction;
    private double increment;


    public MemFunc getMembershipFunction(double value) {
        return new TrapezoidalMemFunc("",getLSupport(value), value, value, getRSupport(value), false, false);
    }

    @Override
    public MemFunc getMembershipFunction() {
        return membershipFunction;
    }

    @Override
    public double getIncrement() {
        return increment;
    }

    @Override
    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public void setMembershipFunction(MemFunc membershipFunction) {
        this.membershipFunction = membershipFunction;
    }

    public FuzzySetImpl(double spread) {
        this.spread = spread;
        this.increment = spread*2/10;
        this.membershipFunction = new PWLMF("",getLSupport(value), value, value, getRSupport(value), false, false, 0, 1);
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

    @Override
    public double getSpread() {
        return spread;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return this.value;
    }
}
