package com.ravi.fuzzyToolBox.TypeReduction;


import com.ravi.fuzzyToolBox.Beans.DeltaZ;
import com.ravi.fuzzyToolBox.Beans.HZ;
import com.ravi.fuzzyToolBox.Beans.ThetaZ;
import com.ravi.fuzzyToolBox.Rules.Consequent;

/**
 * Created by 611445924 on 08/06/2017.
 */
public class CentriodIterMethod {
    private int n;

    private static CentriodIterMethod instance = null;

    protected CentriodIterMethod(int n) {
        this.n = n;
    }

    public static CentriodIterMethod getInstance(){
        if(instance == null){
            instance = new CentriodIterMethod(10000);
        }

        return instance;
    }

    public double getCl(Consequent consequent){
        double yQuote = step1(consequent);//Step 1

        boolean test = true;
        while(test) {
            int e = findE(consequent, yQuote);//Step 2
            double yQQ = step3ForCl(consequent,e);//Step 3

            if(yQQ == yQuote) {//Step 4
                test = false;
            }else
                yQuote = yQQ;//Step 5 and Repeat
        }

        return yQuote;
    }

    public double getCr(Consequent consequent){
        double yQuote = step1(consequent);

        boolean test = true;
        while(test) {
            int e = findE(consequent, yQuote);
            double yQQ = step3ForCr(consequent,e);

            if(yQQ == yQuote) {
                test = false;
            }else
                yQuote = yQQ;
        }

        return yQuote;
    }

    private double step1(Consequent consequent){
        double num = 0.0;
        double den = 0.0;

        for(double yi : consequent.getElements(n)){
            HZ hi = new HZ(consequent.getLowerMemFunc().getMemGrade(yi), consequent.getUpperMemFunc().getMemGrade(yi));
            ThetaZ thetai = new ThetaZ(hi);
            num = num + yi * thetai.getThetaZForStep1();
            den = den + thetai.getThetaZForStep1();
        }

        return num/den;
    }

    private int findE(Consequent consequent, double yQuote){
        double ye = 1.0;
        int yeCount = 1;
        for(double yi : consequent.getElements(n)){
            if(yQuote < yi && yQuote < consequent.getElements(n).get(yeCount)){
                ye = yi;
                break;
            }
            yeCount++;
        }
        return yeCount;
    }

    private double step3ForCl(Consequent consequent, int e){
        double num = 0.0;
        double den = 0.0;

        int z=1;
        for(double yi : consequent.getElements(n)){
            HZ hi = new HZ(consequent.getLowerMemFunc().getMemGrade(yi), consequent.getUpperMemFunc().getMemGrade(yi));
            DeltaZ deltai = new DeltaZ(consequent.getLowerMemFunc().getMemGrade(yi), consequent.getUpperMemFunc().getMemGrade(yi));
            ThetaZ thetai = new ThetaZ(hi,deltai);
            num = num + yi * thetai.getThetaZForCL(z,e);
            den = den + thetai.getThetaZForCL(z,e);
            z++;
        }

        return num/den;
    }

    private double step3ForCr(Consequent consequent, int e){
        double num = 0.0;
        double den = 0.0;

        int z=1;
        for(double yi : consequent.getElements(n)){
            HZ hi = new HZ(consequent.getLowerMemFunc().getMemGrade(yi), consequent.getUpperMemFunc().getMemGrade(yi));
            DeltaZ deltai = new DeltaZ(consequent.getLowerMemFunc().getMemGrade(yi), consequent.getUpperMemFunc().getMemGrade(yi));
            ThetaZ thetai = new ThetaZ(hi,deltai);
            num = num + yi * thetai.getThetaZForCR(z,e);
            den = den + thetai.getThetaZForCR(z,e);
            z++;
        }

        return num/den;
    }
}
