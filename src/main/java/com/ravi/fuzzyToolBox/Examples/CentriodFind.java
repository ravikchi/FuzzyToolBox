package com.ravi.fuzzyToolBox.Examples;

import com.ravi.fuzzyToolBox.MemFunctions.GaussianMemFunc;
import com.ravi.fuzzyToolBox.MemFunctions.MemFunc;
import com.ravi.fuzzyToolBox.Rules.Consequent;
import com.ravi.fuzzyToolBox.TypeReduction.CentriodIterMethod;

/**
 * Created by 611445924 on 08/06/2017.
 */
public class CentriodFind {
    public static void main(String[] args) {

        double m1 = 4.25;
        double m2 = 5.75;
        double sigma = 1.0;
        MemFunc upper = new GaussianMemFunc(m1, m2, sigma);
        MemFunc lower = new GaussianMemFunc(m1+m2/2, m1+m2/2, sigma);
        Consequent consequent = new Consequent(upper, lower);

        System.out.println(CentriodIterMethod.getInstance().getCl(consequent));
        System.out.println(CentriodIterMethod.getInstance().getCr(consequent));
    }
}
