package com.ravi.fuzzyToolBox.FuzzySets;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.PWLMF;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rc16956 on 23/05/2017.
 */
public class FuzzySetImpl implements FuzzySet {
    private double spread;
    private double value;
    private double fou;
    private MemFunc membershipFunction;
    private List<MemFunc> memFuncs;
    private double increment;
    private boolean pwl = false;


    public MemFunc getMembershipFunction(double value) {
        if(!pwl)
            return new PWLMF("",getLSupport(value), value, value, getRSupport(value), false, false, 0, 1);
        else
            return new PWLMF("",getLSupport(value), value-(spread/2), value+(spread/2), getRSupport(value), false, false, 0, 1);
    }

    @Override
    public List<MemFunc> getMemFuncs(double value) {
        memFuncs.clear();
        if(!pwl) {
            memFuncs.add(new PWLMF("",getLSupport(value)-fou, value, value, getRSupport(value)+fou, true, true, 0, 1));
            memFuncs.add(new PWLMF("",getLSupport(value), value, value, getRSupport(value), true, false, 0, 1));
        }else{
            this.memFuncs.add(new PWLMF("", getLSupport(value) - fou, value-(spread/2), value+(spread/2), getRSupport(value) + fou, true, true, 0, 1));
            this.memFuncs.add(new PWLMF("", getLSupport(value), value-(spread/2), value+(spread/2), getRSupport(value), true, false, 0, 1));
        }

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
        this.increment = spread*2/100;
        this.membershipFunction = new PWLMF("",getLSupport(value), value, value, getRSupport(value), false, false, 0, 1);
    }

    public FuzzySetImpl(double spread, boolean pwl) {
        this.spread = spread;
        this.increment = spread*2/100;
        this.pwl = pwl;
        if(!pwl)
            this.membershipFunction = new PWLMF("",getLSupport(value), value, value, getRSupport(value), false, false, 0, 1);
        else
            this.membershipFunction = new PWLMF("",getLSupport(value), value-(spread/2), value+(spread/2), getRSupport(value), false, false, 0, 1);
    }

    public FuzzySetImpl(double spread, double fou, boolean pwl){
        this.spread = spread + fou *2;
        this.fou = fou;
        this.pwl = pwl;
        this.increment = this.spread*2/100;
        this.memFuncs = new ArrayList<MemFunc>();
        if(!pwl) {
            this.membershipFunction = new PWLMF("", getLSupport(value) - fou, value, value, getRSupport(value) + fou, true, true, 0, 1);
            this.memFuncs.add(this.membershipFunction);
            this.memFuncs.add(new PWLMF("", getLSupport(value), value, value, getRSupport(value), true, false, 0, 1));
        }else{
            this.membershipFunction = new PWLMF("", getLSupport(value) - fou, value-(spread/2), value+(spread/2), getRSupport(value) + fou, true, true, 0, 1);
            this.memFuncs.add(this.membershipFunction);
            this.memFuncs.add(new PWLMF("", getLSupport(value), value-(spread/2), value+(spread/2), getRSupport(value), true, false, 0, 1));
        }
    }

    public FuzzySetImpl(List<MemFunc> memFuncs){
        this.memFuncs = memFuncs;
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
