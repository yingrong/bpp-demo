package com.example.bpp.controller;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.service.MonthlyModelRecordService;
import com.example.bpp.service.TaskService;
import com.example.bpp.task.CalculatedTask;
import com.example.bpp.vo.ReportUploadAndDownloadReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Autowired
    private TaskService taskService;

    /**
     * 查询文档审批数据接口 - 导出
     *
     * @return 审核数据
     */
    @ApiOperation(value = "生成初版月结表", notes = "生成初版月结表")
    @PostMapping(value = "init", name = "生成初版月结表")
    void initMonthlyModelRecord() {
        Date now = new Date();
        ReportModelInBo reportModelInBo = new ReportModelInBo()
                .setReportYear(new SimpleDateFormat("yyyy").format(now))
                .setModelMonth(new SimpleDateFormat("MM")
                .format(new Date())).setDealerCode("4181N")
                .setModelMonth(new SimpleDateFormat("MM").format(now));
        monthlyModelRecordService.createMonthlyModelRecord(reportModelInBo);
    }

    /**
     * 查询文档审批数据接口 - 导出
     *
     * @return 审核数据
     */
    @ApiOperation(value = "生成初版月结表", notes = "生成初版月结表")
    @PostMapping(value = "upload", name = "生成初版月结表")
    void uploadMonthlyModelRecord(ReportUploadAndDownloadReqVo reqVo) {
        if (!reqVo.getFile().getOriginalFilename().contains(ExcelTypeEnum.XLSX.getValue()) && !reqVo.getFile().getOriginalFilename().contains(ExcelTypeEnum.XLS.getValue())) {
            return;
        }
        Date now = new Date();
        ReportModelInBo reportModelInBo = new ReportModelInBo()
                .setReportYear(new SimpleDateFormat("yyyy").format(now))
                .setModelMonth(new SimpleDateFormat("MM")
                        .format(new Date())).setDealerCode("4181N")
                .setModelMonth(new SimpleDateFormat("MM").format(now));
        monthlyModelRecordService.uploadMonthlyModelRecord(reqVo, reportModelInBo);
    }

    @ApiOperation(value = "月结表计算", notes = "月结表计算")
    @PostMapping(value = "calculated", name = "月结表计算")
    void calculatedMonthlyRecord() {
        CalculatedTask task = new CalculatedTask(taskService);
        task.execute();
    }


}

