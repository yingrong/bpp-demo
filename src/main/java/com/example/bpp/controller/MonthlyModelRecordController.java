package com.example.bpp.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.bpp.service.MonthlyModelRecordService;
import com.example.bpp.vo.ReportUploadAndDownloadReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuefei
 */
@Slf4j
@Api(value = "月结表服务", tags = "reportSourceModel")
@RequestMapping("api/report-model")
@RestController
public class MonthlyModelRecordController {

    @Autowired
    private MonthlyModelRecordService monthlyModelRecordService;

    /**
     * 查询文档审批数据接口 - 导出
     *
     * @return 审核数据
     */
    @ApiOperation(value = "生成初版月结表", notes = "生成初版月结表")
    @PostMapping(value = "init", name = "生成初版月结表")
    void initMonthlyModelRecord() {
        monthlyModelRecordService.createMonthlyModelRecord();
    }

    /**
     * 查询文档审批数据接口 - 导出
     *
     * @return 审核数据
     */
    @ApiOperation(value = "生成初版月结表", notes = "生成初版月结表")
    @PostMapping(value = "upload", name = "生成初版月结表")
    void uploadMonthlyModelRecord(@Validated ReportUploadAndDownloadReqVo reqVo) {
        if (!reqVo.getFile().getOriginalFilename().contains(ExcelTypeEnum.XLSX.getValue()) && !reqVo.getFile().getOriginalFilename().contains(ExcelTypeEnum.XLS.getValue())) {
            return;
        }
        monthlyModelRecordService.uploadMonthlyModelRecord(reqVo);
    }


}

