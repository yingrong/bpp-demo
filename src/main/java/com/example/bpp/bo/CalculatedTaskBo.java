package com.example.bpp.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@ApiModel(value = "CalculatedTaskBo", description = "计算任务实体")
public class CalculatedTaskBo {

    private String targetIndex;
    /**
     * 目標指標
     */
    private List<String> indexes;
    /**
     * 计算指标map
     */
    private Map<String, ReportModelOutBo> calculatedIndexMap;
    /**
     * 指标数据来源map
     */
    private Map<String, List<ReportModelOutBo>> sourceMap;
}