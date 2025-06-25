package com.example.bpp.salesflow;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SalesBaseRuleTest {

    // 测试 规则是否适用
    @Test
    void isApplicable() {
        SalesBusinessEntity entity = new SalesBusinessEntity();
        entity.setConfSeq("A");
        entity.setOrderType(1);
        entity.setPowerType(1);
        entity.setVehicleTypeCode("A");
        entity.setVehicleTypeName("A");
        entity.setVehicleNameCode("A");
        entity.setVehicleName("A");
        entity.setVehicleSfxCode("A");
        entity.setBigRegionCode("A");
        entity.setBigRegionName("A");
        entity.setSourceLocation("A");
        entity.setSaleDate(new Date());

        SalesBaseRule rule = new SalesBaseRule();
        rule.setOrderType(Arrays.asList(1));
        rule.setPowerType(Arrays.asList(1));
        rule.setTimeRange(TimeRange.closed(Instant.now().minus(1, ChronoUnit.DAYS), Instant.now().plus(1, ChronoUnit.DAYS)));

        assertTrue(rule.isApplicable(entity));
    }


    // 测试 规则是否满足
    @Test
    void evaluate() {
        SalesBusinessEntity entity = new SalesBusinessEntity();
        entity.setConfSeq("A");
        entity.setOrderType(1);
        entity.setPowerType(1);
        entity.setVehicleTypeCode("A");
        entity.setVehicleTypeName("A");
        entity.setVehicleNameCode("A");
        entity.setVehicleName("A");
        entity.setVehicleSfxCode("A");
        entity.setBigRegionCode("A");
        entity.setBigRegionName("北部大区");


        SalesBaseRule rule = new SalesBaseRule();
        rule.setOrderType(Arrays.asList(1));
        rule.setPowerType(Arrays.asList(1));
        rule.setTimeRange(TimeRange.closed(Instant.now().minus(1, ChronoUnit.DAYS), Instant.now().plus(1, ChronoUnit.DAYS)));

        assertTrue(rule.evaluate(entity));
    }

}