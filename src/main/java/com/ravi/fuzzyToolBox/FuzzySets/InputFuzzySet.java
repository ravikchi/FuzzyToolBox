package com.ravi.fuzzyToolBox.FuzzySets;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.PWLMF;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;

import java.util.List;

/**
 * Created by 611445924 on 11/06/2017.
 */
public class InputFuzzySet implements FuzzySet {
    private double spread;
    private double value;
    private MemFunc membershipFunction;
    private double increment;
    private List<MemFunc> memFuncs;
    private double fou;

    public InputFuzzySet(double spread, double value, MemFunc membershipFunction) {
        this.spread = spread;
        this.value = value;
        this.membershipFunction = membershipFunction;
        this.increment = spread*2/100;
    }

    public InputFuzzySet(double spread, double value) {
        this.spread = spread;
        this.value = value;
        this.membershipFunction = new PWLMF("",getLSupport(value), value, value, getRSupport(value), false, false, 0, 1);
        this.increment = spread*2/100;
    }

    public InputFuzzySet(double spread, double value, List<MemFunc> memFuncs){
        this.spread = spread;
        this.value = value;
        this.memFuncs = memFuncs;
        this.increment = spread*2/100;
    }

    @Override
    public List<MemFunc> getMemFuncs(double value) {
        memFuncs.clear();
        memFuncs.add(new PWLMF("",getLSupport(value)-fou, value, value, getRSupport(value)+fou, true, true, 0, 1));
        this.memFuncs.add(new PWLMF("",getLSupport(value), value, value, getRSupport(value), true, false, 0, 1));
        return memFuncs;
    }

    @Override
    public void setMemFuncs(List<MemFunc> memFuncs) {
        this.memFuncs = memFuncs;
    }

    @Override
    public boolean isType2() {
        if(memFuncs != null && memFuncs.size() > 1){
            return true;
        }
        return false;
    }

    public double getSpread() {
        return spread;
    }

    public void setSpread(double spread) {
        this.spread = spread;
    }

    public double getValue() {
        return value;
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
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public MemFunc getMembershipFunction(double value) {
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

    @Override
    public MemFunc getMembershipFunction() {
        return membershipFunction;
    }

    @Override
    public void setMembershipFunction(MemFunc membershipFunction) {
        this.membershipFunction = membershipFunction;
    }
}
