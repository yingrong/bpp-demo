package com.example.bpp.salesflow.core;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Predicate;

@Getter
@Setter
public class BaseRule<T extends BusinessEntity> implements Rule<T> {
    private  String id;
    protected  Predicate<T> condition;
    private  int priority;
    private  boolean negated;
    protected Predicate<T> applicabilityCondition;

    public BaseRule() {
    }

    public BaseRule(String id, Predicate<T> condition, int priority,
                    Predicate<T> applicabilityCondition, boolean negated) {
        this.id = id;
        this.condition = condition;
        this.priority = priority;
        this.applicabilityCondition = applicabilityCondition;
        this.negated = negated;
    }

    @Override
    public boolean evaluate(T entity) {
        boolean result = getCondition().test(entity);
        return negated != result;
    }

    @Override
    public boolean isApplicable(T entity) {
        return getApplicabilityCondition().test(entity);
    }

}