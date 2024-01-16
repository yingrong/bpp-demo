package com.example.bpp.service;

import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.vo.ReportUploadAndDownloadReqVo;

public interface MonthlyModelRecordService {
    void createMonthlyModelRecord(ReportModelInBo reportModelInBo);
    void uploadMonthlyModelRecord(ReportUploadAndDownloadReqVo reqVo, ReportModelInBo reportModelInBo);

}