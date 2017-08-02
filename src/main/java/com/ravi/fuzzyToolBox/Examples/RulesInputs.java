package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.FuzzySets.FuzzySet;
import com.ravi.fuzzyToolBox.FuzzySets.FuzzySetImpl;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.TrapezoidalMemFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 611445924 on 29/05/2017.
 */
public class RulesInputs {
    private FuzzySet x;
    private FuzzySet y;
    private boolean type2 = false;


    private List<MemFunc> low = new ArrayList<MemFunc>();
    private List<MemFunc> mid = new ArrayList<MemFunc>();
    private List<MemFunc> high = new ArrayList<MemFunc>();

    private MemFunc input;

    private List<MemFunc> lowConseqent = new ArrayList<MemFunc>();
    private List<MemFunc> midConseqent = new ArrayList<MemFunc>();
    private List<MemFunc> highConseqent = new ArrayList<MemFunc>();

    public RulesInputs(FuzzySet x, FuzzySet y, double start) {
        this.x = x;
        this.y = y;
        input = new TrapezoidalMemFunc("input",x.getLSupport(start), start, start, x.getRSupport(start), false, false);
    }

    public void defaultMemFunctions(){
        List<MemFunc> low = new ArrayList<MemFunc>();
        MemFunc lowMem = new TrapezoidalMemFunc("lowLower",0, 0, 9, 19, true, false);
        MemFunc lowLower = new TrapezoidalMemFunc("lowUpper",0, 0, 11, 21, true, true);
        low.add(lowMem);
        low.add(lowLower);

        List<MemFunc> lowC = new ArrayList<MemFunc>();
        List<MemFunc> midC = new ArrayList<MemFunc>();
        List<MemFunc> highC = new ArrayList<MemFunc>();

        /*MemFunc lowLowerConsequent = new TrapezoidalMemFunc("lowConsequent", 0, 0, 9, 19, true, false);
        MemFunc lowUpperConsequent = new TrapezoidalMemFunc("lowUpperConsequent",0, 0, 11, 21, true, true);
        lowC.add(lowLowerConsequent);
        lowC.add(lowUpperConsequent);*/

        MemFunc lowLowerConsequent = new TrapezoidalMemFunc("lowConsequent", 0, 40, 40, 19, false, false);
        lowC.add(lowLowerConsequent);

        List<MemFunc> mid = new ArrayList<MemFunc>();
        MemFunc midMem = new TrapezoidalMemFunc("midUpper",10, 20, 31, 41, true, true);
        MemFunc midLower = new TrapezoidalMemFunc("midLower",12, 22, 29, 39, true, false);
        mid.add(midMem);
        mid.add(midLower);

        /*MemFunc midLowerConsequent = new TrapezoidalMemFunc("midConsequent", 12, 22, 29, 39, true, false);
        MemFunc midUpperConsequent = new TrapezoidalMemFunc("midUpperConsequent",10, 20, 31, 41, true, true);
        midC.add(midLowerConsequent);
        midC.add(midUpperConsequent);*/

        MemFunc midUpperConsequent = new TrapezoidalMemFunc("midUpperConsequent",10, 70, 70, 41, false, true);
        midC.add(midUpperConsequent);

        List<MemFunc> high = new ArrayList<MemFunc>();
        MemFunc highMem = new TrapezoidalMemFunc("highUpper",30, 40, 50, 50, true, true);
        MemFunc highLower = new TrapezoidalMemFunc("highLower",32, 42, 50, 50, true, false);
        high.add(highMem);
        high.add(highLower);

        /*MemFunc highMemConsequent = new TrapezoidalMemFunc("highUpperConsequent",30, 40, 50, 50, true, true);
        MemFunc highLowerConsequent = new TrapezoidalMemFunc("highLowerConsequent",32, 42, 50, 50, true, false);
        highC.add(highMemConsequent);
        highC.add(highLowerConsequent);*/

        MemFunc highLowerConsequent = new TrapezoidalMemFunc("highLowerConsequent",32, 100, 100, 50, false, false);
        highC.add(highLowerConsequent);

        this.setLow(low);
        this.setMid(mid);
        this.setHigh(high);

        this.setLowConseqent(lowC);
        this.setMidConseqent(midC);
        this.setHighConseqent(highC);
    }

    public void type1MemFunctions(){
        List<MemFunc> low = new ArrayList<MemFunc>();
        MemFunc lowMem = new TrapezoidalMemFunc("lowLower",0, 0, 11, 20, false, true);
        //MemFunc lowLower = new TrapezoidalMemFunc("lowUpper",0, 0, 11, 21, true, true);
        low.add(lowMem);
        //low.add(lowLower);

        List<MemFunc> lowC = new ArrayList<MemFunc>();
        List<MemFunc> midC = new ArrayList<MemFunc>();
        List<MemFunc> highC = new ArrayList<MemFunc>();

        /*MemFunc lowLowerConsequent = new TrapezoidalMemFunc("lowConsequent", 0, 0, 9, 19, true, false);
        MemFunc lowUpperConsequent = new TrapezoidalMemFunc("lowUpperConsequent",0, 0, 11, 21, true, true);
        lowC.add(lowLowerConsequent);
        lowC.add(lowUpperConsequent);*/

        MemFunc lowLowerConsequent = new TrapezoidalMemFunc("lowConsequent", 0, 40, 40, 19, false, true);
        lowC.add(lowLowerConsequent);

        List<MemFunc> mid = new ArrayList<MemFunc>();
        MemFunc midMem = new TrapezoidalMemFunc("midUpper",11, 21, 30, 40, false, true);
        //MemFunc midLower = new TrapezoidalMemFunc("midLower",12, 22, 29, 39, true, false);
        mid.add(midMem);
        //mid.add(midLower);

        /*MemFunc midLowerConsequent = new TrapezoidalMemFunc("midConsequent", 12, 22, 29, 39, true, false);
        MemFunc midUpperConsequent = new TrapezoidalMemFunc("midUpperConsequent",10, 20, 31, 41, true, true);
        midC.add(midLowerConsequent);
        midC.add(midUpperConsequent);*/

        MemFunc midUpperConsequent = new TrapezoidalMemFunc("midUpperConsequent",10, 70, 70, 41, false, true);
        midC.add(midUpperConsequent);

        List<MemFunc> high = new ArrayList<MemFunc>();
        MemFunc highMem = new TrapezoidalMemFunc("highUpper",31, 41, 50, 50, false, true);
        //MemFunc highLower = new TrapezoidalMemFunc("highLower",32, 42, 50, 50, true, false);
        high.add(highMem);
        //high.add(highLower);

        /*MemFunc highMemConsequent = new TrapezoidalMemFunc("highUpperConsequent",30, 40, 50, 50, true, true);
        MemFunc highLowerConsequent = new TrapezoidalMemFunc("highLowerConsequent",32, 42, 50, 50, true, false);
        highC.add(highMemConsequent);
        highC.add(highLowerConsequent);*/

        MemFunc highLowerConsequent = new TrapezoidalMemFunc("highLowerConsequent",32, 100, 100, 50, false, true);
        highC.add(highLowerConsequent);

        this.setLow(low);
        this.setMid(mid);
        this.setHigh(high);

        this.setLowConseqent(lowC);
        this.setMidConseqent(midC);
        this.setHighConseqent(highC);
    }

    public MemFunc getInput() {
        return input;
    }

    public void setInput(MemFunc input) {
        this.input = input;
    }

    public FuzzySet getX() {
        return x;
    }

    public void setX(FuzzySet x) {
        this.x = x;
    }

    public FuzzySet getY() {
        return y;
    }

    public void setY(FuzzySet y) {
        this.y = y;
    }

    public List<MemFunc> getLow() {
        return low;
    }

    public void setLow(List<MemFunc> low) {
        this.low = low;
    }

    public List<MemFunc> getMid() {
        return mid;
    }

    public void setMid(List<MemFunc> mid) {
        this.mid = mid;
    }

    public List<MemFunc> getHigh() {
        return high;
    }

    public void setHigh(List<MemFunc> high) {
        this.high = high;
    }

    public List<MemFunc> getLowConseqent() {
        return lowConseqent;
    }

    public void setLowConseqent(List<MemFunc> lowConseqent) {
        this.lowConseqent = lowConseqent;
    }

    public List<MemFunc> getMidConseqent() {
        return midConseqent;
    }

    public void setMidConseqent(List<MemFunc> midConseqent) {
        this.midConseqent = midConseqent;
    }

    public List<MemFunc> getHighConseqent() {
        return highConseqent;
    }

    public void setHighConseqent(List<MemFunc> highConseqent) {
        this.highConseqent = highConseqent;
    }
}
