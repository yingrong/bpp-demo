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
@ApiModel(value = "DepIndexConfPo", description = "指标依赖配置实体")
@TableName(value = "t_bpp_dep_index_conf", autoResultMap = true)
public class DepIndexConfPo {

    @ApiModelProperty("指标（目标）")
    @TableField(value = "target_index")
    private String targetIndex;
    @ApiModelProperty("指标（来源）")
    @TableField(value = "source_index")
    private String sourceIndex;
    @ApiModelProperty("更新次数")
    @TableField(value = "update_count")
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
