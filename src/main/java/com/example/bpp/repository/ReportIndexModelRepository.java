package com.example.bpp.repository;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.bo.ReportModelOutBo;
import com.example.bpp.entity.ReportIndexModelPo;
import com.example.bpp.mapper.ReportIndexModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuefei
 */
@Service
public class ReportIndexModelRepository extends ServiceImpl<ReportIndexModelMapper, ReportIndexModelPo> {

    public List<ReportModelOutBo> queryMonthlyModelRecord(ReportModelInBo reportModelInBo) {
        return baseMapper.queryMonthlyModelRecord(reportModelInBo);
    }

    public List<ReportModelOutBo> queryCalculatedTargetIndexInfo() {
        return baseMapper.queryCalculatedTargetIndexInfo();
    }

    public List<ReportModelOutBo> queryCalculatedBasicInfoByTargetIndex(ReportModelInBo reportModelInBo) {
        return baseMapper.queryCalculatedBasicInfoByTargetIndex(reportModelInBo);
    }

}
