package com.ravi.fuzzyToolBox.Rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rc16956 on 23/05/2017.
 */
public class Rules {
    private List<Rule> rules = new ArrayList<Rule>();

    public List<Rule> getRules() {
        return rules;
    }

    public Rule getRule(int i){
        return this.rules.get(i);
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void addRule(Rule rule){
        this.rules.add(rule);
    }

    public int size(){
        return this.rules.size();
    }
}

