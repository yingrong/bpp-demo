package com.example.bpp.salesflow;

import com.example.bpp.salesflow.core.BaseRule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesBaseRule extends BaseRule<SalesBusinessEntity> {

    private String id;
    // 生效时间区间
    private TimeRange timeRange;
    // applicabilityCondition
    private String confSeq;
    private List<Integer> orderType;
    private List<Integer> powerType;
    private String vehicleTypeCode;
    private List<String> vehicleTypeName;
    private List<String> vehicleNameCode;
    private String vehicleName;
    private List<String> vehicleSfxCode;

    // 基础规则
    private Integer basicScope;
    // 总部关注规则
    private Integer headOfficeFocusScope;
    // 特殊规则
    private String bigRegionCode;
    private String bigRegionName;
    private String sourceLocation;
    private String targetLocation;
    private String excludedSourceLocation;
    private String excludedTargetLocation;
    private String sourceDealerCode;
    private String sourceDealerName;

    @Override
    public Predicate<SalesBusinessEntity> getApplicabilityCondition() {
        return salesBusinessEntity -> {
            if (!timeRange.contains(salesBusinessEntity.getSaleDate())) {
                return false;
            }

            if (isNotEmpty(orderType) && !orderType.contains(salesBusinessEntity.getOrderType())) {
                return false;
            }

            if (isNotEmpty(powerType) && !powerType.contains(salesBusinessEntity.getPowerType())) {
                return false;
            }

            if (isNotEmpty(vehicleTypeName) && !vehicleTypeName.contains(salesBusinessEntity.getVehicleTypeName())) {
                return false;
            }

            if (isNotEmpty(vehicleNameCode) && !vehicleNameCode.contains(salesBusinessEntity.getVehicleNameCode())) {
                return false;
            }

            if (isNotEmpty(vehicleSfxCode) && !vehicleSfxCode.contains(salesBusinessEntity.getVehicleSfxCode())) {
                return false;
            }

            return true;
        };
    }

    @Override
    public Predicate<SalesBusinessEntity> getCondition() {
        return salesBusinessEntity -> {
            // 1 判断是否符合基础规则，如果符合，就返回true
//            if (basicScope == 1) {
                if (salesBusinessEntity.getSourceLocation() == salesBusinessEntity.getTargetLocation()) {
                    return true;
                }
//            }

            // 2 判断是否符合总部关注规则，如果符合，就返回true

            // 3 判断是否符合特殊规则，如果符合，就返回true


            return false;
        };
    }

}
