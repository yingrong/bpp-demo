package com.example.bpp.salesflow;

import com.example.bpp.salesflow.core.BusinessEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 有效日期：起始日期-结束日期，格式为：YYYY-MM-DD HH:MM:SS - YYYY-MM-DD HH:MM:SS
 * 车种：下拉栏，可多选，默认值为主数据全部车种
 * 车型代码：下拉栏，可多选，默认值为全部车型代码
 * SFX：下拉栏，可多选，默认值为全部SFX
 * 销售类型：下拉栏，可多选，默认值为全部销售类型
 * 燃油类型：下拉栏，可多选，默认值为全部燃油类型
 * 地区范围：下拉栏，默认值为全国，下拉选项为：全国、大区内、省内、市内
 */
@Getter
@Setter
public class SalesBusinessEntity implements BusinessEntity {


    private String vin;
    private Date saleDate;
    private String vehicleType;
    private String sfx;
    private String color;
    private String fuelType;
    private String salesType;
    private String daqu;
    private String province;
    private String city;

}
