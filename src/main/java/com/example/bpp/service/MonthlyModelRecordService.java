package com.example.bpp.service;

import com.example.bpp.vo.ReportUploadAndDownloadReqVo;

public interface MonthlyModelRecordService {
    void createMonthlyModelRecord();
    void uploadMonthlyModelRecord(ReportUploadAndDownloadReqVo reqVo);

}