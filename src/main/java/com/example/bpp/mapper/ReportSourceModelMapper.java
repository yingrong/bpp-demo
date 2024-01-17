package com.example.bpp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.bo.ReportModelOutBo;
import com.example.bpp.entity.ReportSourceModelPo;

import java.util.List;

/**
 * 报表数据来源明细Mapper
 * @author xuefei
 */
public interface ReportSourceModelMapper extends BaseMapper<ReportSourceModelPo> {

    /**
     * 获取月结表数据
     * @return
     */
    List<ReportModelOutBo> queryMonthlyModelRecord();

    /**
     * 获取需要计算的指标
     * @return
     */
    List<ReportModelOutBo> queryCalculatedTargetIndexInfo();

    /**
     * 获取指标计算的基础数据
     * @param reportModelInBo
     * @return
     */
    List<ReportModelOutBo> queryCalculatedBasicInfoByTargetIndex(ReportModelInBo reportModelInBo);
}
