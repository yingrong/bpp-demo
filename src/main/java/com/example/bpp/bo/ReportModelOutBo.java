package com.example.bpp.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xuefei
 */
@Data
@ApiModel(value = "ReportModelOutBo", description = "报表数据导入模板实体")
public class ReportModelOutBo {

    @ApiModelProperty("来源模版表编号")
    private String targetIndex;
    @ApiModelProperty("来源模版表编号")
    private String dataSourceIndex;
    @ApiModelProperty("来源模版表编号")
    private String sheetCode;
    @ApiModelProperty("单元格坐标")
    private String cellCoord;
    @ApiModelProperty("填报值")
    private Double fillValue;
    @ApiModelProperty("公式参数")
    private String formulaParam;
    @ApiModelProperty("公式")
    private String indexFormula;


}
