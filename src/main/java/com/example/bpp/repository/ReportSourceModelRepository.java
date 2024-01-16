package com.example.bpp.repository;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.bo.ReportModelOutBo;
import com.example.bpp.entity.ReportSourceModelPo;
import com.example.bpp.mapper.ReportSourceModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuefei
 */
@Service
public class ReportSourceModelRepository extends ServiceImpl<ReportSourceModelMapper, ReportSourceModelPo> {

    public List<ReportModelOutBo> queryMonthlyModelRecord() {
        return baseMapper.queryMonthlyModelRecord();
    }

    public List<ReportModelOutBo> queryCalculatedTargetIndexInfo() {
        return baseMapper.queryCalculatedTargetIndexInfo();
    }

    public List<ReportModelOutBo> queryCalculatedBasicInfoByTargetIndex(ReportModelInBo reportModelInBo) {
        return baseMapper.queryCalculatedBasicInfoByTargetIndex(reportModelInBo);
    }

}
