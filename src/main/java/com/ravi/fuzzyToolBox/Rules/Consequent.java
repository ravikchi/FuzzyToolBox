package com.ravi.fuzzyToolBox.Rules;

import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 25/05/2017.
 */
public class Consequent {
    private MemFunc upperMemFunc;
    private MemFunc lowerMemFunc;
    private MemFunc memFunc;
    private boolean isType2 = false;
    private List<Double> elements;


    public Consequent(MemFunc upperMemFunc, MemFunc lowerMemFunc) {
        this.upperMemFunc = upperMemFunc;
        this.lowerMemFunc = lowerMemFunc;
        this.isType2 = true;
    }

    public List<Double> getElements(int n){
        if(elements == null){
            elements = new ArrayList<Double>();

            double start = Math.min(lowerMemFunc.getStart(), upperMemFunc.getStart());
            double end = Math.max(lowerMemFunc.getEnd(), upperMemFunc.getEnd());

            double inc = (start + end)/(n-1);

            for(double i = start; i<=end; i = i + inc){
                elements.add(i);
            }
        }

        return elements;
    }

    public boolean isType2() {
        return isType2;
    }

    public void setType2(boolean type2) {
        isType2 = type2;
    }

    public Consequent(List<MemFunc> memFuncs) {
        for(MemFunc memFunc : memFuncs) {
            if(memFunc.isType2()) {
                this.isType2 = true;
                if (memFunc.isUpper()) {
                    this.upperMemFunc = memFunc;
                }else{
                    this.lowerMemFunc = memFunc;
                }
            }else {
                this.memFunc = memFunc;
            }
        }
    }

    public MemFunc getUpperMemFunc() {
        return upperMemFunc;
    }

    public void setUpperMemFunc(MemFunc upperMemFunc) {
        this.upperMemFunc = upperMemFunc;
    }

    public MemFunc getLowerMemFunc() {
        return lowerMemFunc;
    }

    public void setLowerMemFunc(MemFunc lowerMemFunc) {
        this.lowerMemFunc = lowerMemFunc;
    }

    public MemFunc getMemFunc() {
        return memFunc;
    }

    public void setMemFunc(MemFunc memFunc) {
        this.memFunc = memFunc;
    }
}
