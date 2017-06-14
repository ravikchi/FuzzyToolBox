package com.ravi.fuzzyToolBox;

import com.ravi.fuzzyToolBox.Rules.Rule;

import java.util.Comparator;

/**
 * Created by 611445924 on 12/06/2017.
 */
public class RulesComparator implements Comparator<Rule> {

    public int compare(Rule rule1, Rule rule2){
        if(rule1.getOrder() <  rule2.getOrder()) return -1;
        if(rule1.getOrder() == rule2.getOrder()) return 0;
        return 1;
    }
}
