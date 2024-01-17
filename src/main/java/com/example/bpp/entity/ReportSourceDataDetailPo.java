package com.example.bpp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuefei
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ReportSourceDataDetailPo", description = "报表数据来源明细数据实体")
@TableName(value = "t_bpp_report_source_data_detail", autoResultMap = true)
public class ReportSourceDataDetailPo {

    @ApiModelProperty("期间年")
    @TableField(value = "report_year")
    private String reportYear;
    @ApiModelProperty("期间月")
    @TableField(value = "report_month")
    private String reportMonth;
    @ApiModelProperty("经销店编码")
    @TableField(value = "dealer_code")
    private String dealerCode;
    @ApiModelProperty("来源模版表编号")
    @TableField(value = "sheet_code")
    private String sheetCode;
    @ApiModelProperty("模板月份")
    @TableField(value = "model_month")
    private String modelMonth;
    @ApiModelProperty("指标")
    @TableField(value = "index_name")
    private String indexName;
    @ApiModelProperty("维度类型")
    @TableField(value = "dim_type")
    private String dimType;
    @ApiModelProperty("维度")
    @TableField(value = "dim_name")
    private String dimName;
    @ApiModelProperty("填报值")
    @TableField(value = "fill_value")
    private Double fillValue;
    @ApiModelProperty("更新次数")
    @TableField(value = "update_count")
    @Version
    private int updateCount;

    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;
    @TableField(
            value = "create_by",
            fill = FieldFill.INSERT
    )
    private Long createBy;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @TableField(
            value = "create_time",
            fill = FieldFill.INSERT
    )
    private Date createTime;
    @TableField(
            value = "update_by",
            fill = FieldFill.INSERT_UPDATE
    )
    private Long updateBy;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @TableField(
            value = "update_time",
            fill = FieldFill.INSERT_UPDATE
    )
    private Date updateTime;
    @TableField("remark")
    private String remark;
    @TableField(
            value = "del_flag",
            fill = FieldFill.INSERT_UPDATE
    )
    @TableLogic
    private Integer delFlag;
}
