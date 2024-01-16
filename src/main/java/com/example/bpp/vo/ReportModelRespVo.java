package com.example.bpp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xuefei
 */
@Data
@ApiModel(value = "ReportModelRespVo", description = "报表数据导入模板实体")
public class ReportModelRespVo {

    @ApiModelProperty("期间月")
    private String reportMonth;
    @ApiModelProperty("来源模版表编号")
    private String sheetCode;
    @ApiModelProperty("单元格坐标")
    private String cellCoord;
    @ApiModelProperty("填报值")
    private BigDecimal fillValue;


}
