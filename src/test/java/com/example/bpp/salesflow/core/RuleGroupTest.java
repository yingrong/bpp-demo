package com.example.bpp.salesflow.core;

import com.example.bpp.salesflow.SalesBusinessEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleGroupTest {

    // 新建一个规则组，有两条规则，关系为OR，即，任意规则返回true，则返回true
    @Test
    void testEvaluateWithOperatorOR1() {
        RuleGroup<SalesBusinessEntity> ruleGroup = new RuleGroup<>("ruleGroup", LogicalOperator.OR, 1,
                entity -> true, false);

        ruleGroup.addRule(new BaseRule<>("rule1", entity -> false, 1, entity -> true, false));
        ruleGroup.addRule(new BaseRule<>("rule2", entity -> true, 1, entity -> true, false));

        assertTrue(ruleGroup.evaluate(new SalesBusinessEntity()));
    }

    @Test
    public void testRuleGroupWithOrOperatorOR2() {
        RuleGroup<SalesBusinessEntity> ruleGroup = new RuleGroup<>("ruleGroup", LogicalOperator.OR, 1,
                entity -> true, false);

        ruleGroup.addRule(new BaseRule<>("rule1", entity -> false, 1, entity -> true, false));
        ruleGroup.addRule(new BaseRule<>("rule2", entity -> false, 1, entity -> true, false));

        assertFalse(ruleGroup.evaluate(new SalesBusinessEntity()));
    }

    @Test
    void testEvaluateWithOperatorAND1() {
        RuleGroup<SalesBusinessEntity> ruleGroup = new RuleGroup<>("ruleGroup", LogicalOperator.AND, 1,
                entity -> true, false);

        ruleGroup.addRule(new BaseRule<>("rule1", entity -> false, 1, entity -> true, false));
        ruleGroup.addRule(new BaseRule<>("rule2", entity -> true, 1, entity -> true, false));

        assertFalse(ruleGroup.evaluate(new SalesBusinessEntity()));
    }

    @Test
    void testEvaluateWithOperatorAND2() {
        RuleGroup<SalesBusinessEntity> ruleGroup = new RuleGroup<>("ruleGroup", LogicalOperator.AND, 1,
                entity -> true, false);

        ruleGroup.addRule(new BaseRule<>("rule1", entity -> true, 1, entity -> true, false));
        ruleGroup.addRule(new BaseRule<>("rule2", entity -> true, 1, entity -> true, false));

        assertTrue(ruleGroup.evaluate(new SalesBusinessEntity()));
    }

}