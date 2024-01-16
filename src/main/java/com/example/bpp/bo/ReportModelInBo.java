package com.example.bpp.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Set;

/**
 * @author xuefei
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ReportModelInBo", description = "报表数据导入模板实体")
public class ReportModelInBo {
    @ApiModelProperty("来源模版表编号")
    private Set<String> targetIndexList;
    @ApiModelProperty("期间年")
    private String reportYear;
    @ApiModelProperty("期间月")
    private String reportMonth;
    @ApiModelProperty("经销店编码")
    private String dealerCode;
    @ApiModelProperty("来源模版表编号")
    private String sheetCode;
    @ApiModelProperty("模板月份")
    private String modelMonth;

}
