package com.example.bpp.salesflow;

import com.example.bpp.salesflow.core.BaseRule;
import com.example.bpp.salesflow.core.BusinessEntity;
import com.example.bpp.salesflow.core.Rule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RuleEngine {

    private final List<Rule<? extends BusinessEntity>> rules = new ArrayList<>();
    public void addRule(BaseRule<BusinessEntity> rule) {

        rules.add(rule);
        rules.sort(Comparator.comparingInt(Rule::getPriority));
    }
}
