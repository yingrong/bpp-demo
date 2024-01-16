package com.example.bpp.service;

import com.example.bpp.bo.ReportModelOutBo;

import java.util.List;

public interface ReportIndexDataDetailService {
    /**
     * 获取指标计算数据
     */
    boolean addBathReportSourceDataDetail(List<ReportModelOutBo> list);

    boolean addReportSourceDataDetail(ReportModelOutBo reportModelOutBo);
}