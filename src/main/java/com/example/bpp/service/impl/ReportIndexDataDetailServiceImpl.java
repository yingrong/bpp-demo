package com.example.bpp.service.impl;

import com.example.bpp.bo.ReportModelOutBo;
import com.example.bpp.entity.ReportIndexDataDetailPo;
import com.example.bpp.repository.ReportIndexDataDetailRepository;
import com.example.bpp.service.ReportIndexDataDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author xuefei
 */
@Slf4j
@Service
public class ReportIndexDataDetailServiceImpl implements ReportIndexDataDetailService {

    @Resource
    private ReportIndexDataDetailRepository reportIndexDataDetailRepository;

    @Override
    public boolean addBathReportSourceDataDetail(List<ReportModelOutBo> list) {
        Date date = new Date();
        List<ReportIndexDataDetailPo> saveList = new ArrayList<>();
        for (ReportModelOutBo bo : list) {
            ReportIndexDataDetailPo po = new ReportIndexDataDetailPo()
//                        .setReportYear()
//                        .setReportMonth()
//                        .setDealerCode()
                    .setSheetCode(bo.getSheetCode())
//                        .setModelMonth()
                    .setIndexName(bo.getTargetIndex())
//                        .setDimType()
//                        .setDimName()
                    .setFillValue(bo.getFillValue())
//                    .setUpdateCount(0);
                    .setCreateTime(date)
                    .setCreateBy(-1L)
                    .setUpdateTime(date)
                    .setUpdateBy(-1L);
            saveList.add(po);
        }
        return reportIndexDataDetailRepository.saveBatch(saveList);
    }

    @Override
    public boolean addReportSourceDataDetail(ReportModelOutBo bo) {
        Date date = new Date();
        ReportIndexDataDetailPo po = new ReportIndexDataDetailPo()
//                        .setReportYear()
//                        .setReportMonth()
//                        .setDealerCode()
                .setSheetCode(bo.getSheetCode())
//                        .setModelMonth()
                .setIndexName(bo.getTargetIndex())
//                        .setDimType()
//                        .setDimName()
                .setFillValue(bo.getFillValue())
//                    .setUpdateCount(0);
                .setCreateTime(date)
                .setCreateBy(-1L)
                .setUpdateTime(date)
                .setUpdateBy(-1L);
        return reportIndexDataDetailRepository.save(po);
    }

}
