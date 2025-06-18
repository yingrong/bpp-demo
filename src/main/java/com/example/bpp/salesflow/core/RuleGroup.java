package com.example.bpp.salesflow.core;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RuleGroup<T extends BusinessEntity> extends BaseRule<T> {
    @Getter
    private final LogicalOperator operator;
    private final List<Rule<T>> rules = new ArrayList<>();

    public RuleGroup(String id, LogicalOperator operator, int priority, Predicate<T> applicabilityCondition, boolean negated) {
        super(id, t -> true, priority, applicabilityCondition, negated);
        this.operator = operator;
    }

    public void addRule(Rule<T> rule) {
        rules.add(rule);
    }

    @Override
    public boolean evaluate(T entity) {
        if (rules.isEmpty()) {
            return false;
        }

        if (operator == LogicalOperator.AND) {
            boolean result = rules.stream().allMatch(rule -> rule.evaluate(entity));
            return isNegated() != result;
        } else if (operator == LogicalOperator.OR) {
            boolean result = rules.stream().anyMatch(rule -> rule.evaluate(entity));
            return isNegated() != result;
        } else {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

}

