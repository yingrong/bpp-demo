package com.example.bpp.salesflow;

import com.example.bpp.salesflow.core.BaseRule;
import com.example.bpp.salesflow.core.BusinessEntity;
import com.example.bpp.salesflow.core.Rule;

import java.util.*;

public class RuleEngine {

    private final List<Rule<? extends BusinessEntity>> rules = new ArrayList<>();

    public void addRule(BaseRule<BusinessEntity> rule) {
        rules.add(rule);
        rules.sort(Comparator.comparingInt(Rule::getPriority));
    }

    public <T extends BusinessEntity> Map<Rule, Boolean> evaluateAll(T entity) {
        Map<Rule, Boolean> ruleEvaluationResults = new HashMap<>();

        if (entity == null) {
            return ruleEvaluationResults;
        }

        for (Rule rule : rules) {
            if (rule.isApplicable(entity)) {
                ruleEvaluationResults.put(rule, rule.evaluate(entity));
            }
        }

        return ruleEvaluationResults;
    }

}
