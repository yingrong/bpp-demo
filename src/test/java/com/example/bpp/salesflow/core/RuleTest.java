package com.example.bpp.salesflow.core;

import com.example.bpp.salesflow.SalesBusinessEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    // 新建一个规则，传入业务对象，这条规则是否处理这个业务
    @Test
    void testIsApplicable() {
        SalesBusinessEntity salesBusinessEntity = new SalesBusinessEntity();
        salesBusinessEntity.setDaqu("1");

        BaseRule<SalesBusinessEntity> rule1 = new BaseRule<>("rule1", entity -> true, 1, entity -> entity.getDaqu().equals("1"), false);
        assertTrue(rule1.isApplicable(salesBusinessEntity));
    }

    @Test
    void testIsNotApplicable() {
        SalesBusinessEntity salesBusinessEntity = new SalesBusinessEntity();
        salesBusinessEntity.setDaqu("2");

        BaseRule<SalesBusinessEntity> rule = new BaseRule<>("rule1", entity -> true, 1, entity -> entity.getDaqu().equals("1"), false);

        assertFalse(rule.isApplicable(salesBusinessEntity));
    }

    // 当某个业务适用某条规则时（isApplicable），是否符合业务规则

    @Test
    void testEvaluate() {
        SalesBusinessEntity salesBusinessEntity = new SalesBusinessEntity();
        salesBusinessEntity.setDaqu("1");
        salesBusinessEntity.setCity("北京");

        BaseRule<SalesBusinessEntity> rule = new BaseRule<>("rule1", entity -> {
            return entity.getCity().equals("北京");
        }, 1, entity -> entity.getDaqu().equals("1"), false);

        assertTrue(rule.evaluate(salesBusinessEntity));
    }

    @Test
    void testEvaluateNegated() {
        SalesBusinessEntity salesBusinessEntity = new SalesBusinessEntity();
        salesBusinessEntity.setDaqu("1");
        salesBusinessEntity.setCity("北京");

        BaseRule<SalesBusinessEntity> rule = new BaseRule<>("rule1", entity -> {
            return entity.getCity().equals("北京");
        }, 1, entity -> entity.getDaqu().equals("1"), true);

        assertFalse(rule.evaluate(salesBusinessEntity));
    }

}