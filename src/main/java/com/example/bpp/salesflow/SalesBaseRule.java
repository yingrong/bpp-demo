package com.example.bpp.salesflow;

import com.example.bpp.salesflow.core.BaseRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public class SalesBaseRule extends BaseRule<SalesBusinessEntity> {

    private String sfx;
    private String daqu;
    private TimeRange timeRange;
    private int priority;


    @Override
    public Predicate<SalesBusinessEntity> getApplicabilityCondition() {
        return salesBusinessEntity -> {
            if (!this.timeRange.contains(salesBusinessEntity.getSaleDate())) {
                return false;
            }

            if (this.sfx != null && !this.sfx.equals(salesBusinessEntity.getSfx())) {
                return false;
            }
            if (this.daqu != null && !this.daqu.equals(salesBusinessEntity.getDaqu())) {
                return false;
            }
            return true;
        };
    }

}
