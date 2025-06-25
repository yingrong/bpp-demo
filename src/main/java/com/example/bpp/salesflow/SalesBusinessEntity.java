package com.example.bpp.salesflow;

import com.example.bpp.salesflow.core.BusinessEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 */
@Getter
@Setter
public class SalesBusinessEntity implements BusinessEntity {

    private String vin;
    private String confSeq;
    private Integer orderType;
    private Integer powerType;
    private String vehicleTypeCode;
    private String vehicleTypeName;
    private String vehicleNameCode;
    private String vehicleName;
    private String vehicleSfxCode;
    private String bigRegionCode;
    private String bigRegionName;
    private String sourceLocation;
    private String targetLocation;
    private Date saleDate;

}
