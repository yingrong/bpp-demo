package com.example.bpp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author xuefei
 */
@Data
@ApiModel(value = "ReportSourceModelPo", description = "报表数据导入模板实体")
@TableName(value = "t_bpp_report_source_model", autoResultMap = true)
public class ReportSourceModelPo {

    @ApiModelProperty("类型")
    @TableField(value = "type")
    private String type;
    @ApiModelProperty("来源模版表编号")
    @TableField(value = "sheet_code")
    private String sheetCode;
    @ApiModelProperty("模板月份")
    @TableField(value = "model_month")
    private String modelMonth;
    @ApiModelProperty("指标")
    @TableField(value = "index_name")
    private String indexName;
    @ApiModelProperty("单元格坐标")
    @TableField(value = "cell_coord")
    private String cellCoord;
    @ApiModelProperty("车型")
    @TableField(value = "vehicle_model_code")
    private String vehicleModelCode;
    @ApiModelProperty("更新次数")
    @TableField(value = "update_count")
    private String updateCount;

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
