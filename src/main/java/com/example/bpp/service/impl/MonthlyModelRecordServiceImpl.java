package com.example.bpp.service.impl;

import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.bo.ReportModelOutBo;
import com.example.bpp.repository.DepIndexConfRepository;
import com.example.bpp.repository.ReportIndexModelRepository;
import com.example.bpp.service.MonthlyModelRecordService;
import com.example.bpp.service.ReportIndexDataDetailService;
import com.example.bpp.service.TaskService;
import com.example.bpp.util.ExcelUtil;
import com.example.bpp.vo.ReportUploadAndDownloadReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author xuefei
 */
@Slf4j
@Service
public class MonthlyModelRecordServiceImpl implements MonthlyModelRecordService {

    @Resource
    private ReportIndexModelRepository reportIndexModelRepository;
    @Resource
    private DepIndexConfRepository depIndexConfRepository;
    @Resource
    private ReportIndexDataDetailService reportIndexDataDetailService;
    @Autowired
    private TaskService taskService;


    @Override
    @Transactional
    public void createMonthlyModelRecord(ReportModelInBo reportModelInBo) {
        List<ReportModelOutBo> reportModelRespVoList = reportIndexModelRepository.queryMonthlyModelRecord(reportModelInBo);
        XSSFWorkbook workbook = null;
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            File file = new File("E://新建文件夹//FILEC04001-经销店月结表_2024-01_65432_经销商名称-样例.xlsx");
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            exportExcelDataProcess(reportModelRespVoList, workbook);
            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            File output = new File("E://" + dateStr + ".xlsx");
            fileOutputStream = new FileOutputStream(output);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            log.error("", e);
            // TODO 保存数据  月结表模板生成记录表 t_bpp_monthly_model_create_record
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    @Override
    @Transactional
    public void uploadMonthlyModelRecord(ReportUploadAndDownloadReqVo reqVo, ReportModelInBo reportModelInBo) {
        // TODO 按年份查询 返回月份组装指标数据
        MultipartFile file = reqVo.getFile();
        XSSFWorkbook workbook = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = file.getInputStream();
            ZipSecureFile.setMinInflateRatio(0.001);
            workbook = new XSSFWorkbook(inputStream);
            StringBuilder errorInfo = existingDataVerification(workbook, reportModelInBo);
            if (StringUtils.isNotBlank(errorInfo)) {
                return;
            }
            // 保存上传指标数据
            boolean saveTag = saveUploadExcelIndo(workbook);
            if (!saveTag) {
                return;
            }
            // 获取最新数据计算指标
//            new CalculatedTask(taskService).execute();
            List<ReportModelOutBo> targetIndexInfoBos = reportIndexModelRepository.queryCalculatedTargetIndexInfo();

            exportExcelDataProcess(targetIndexInfoBos, workbook);
            String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            File output = new File("E://" + dateStr + ".xlsx");
            fileOutputStream = new FileOutputStream(output);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        } catch (IOException e) {
            log.error("", e);
            // TODO 保存数据  月结表模板生成记录表	t_bpp_monthly_model_create_record
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    private static void exportExcelDataProcess(List<ReportModelOutBo> reportModelRespVoList, XSSFWorkbook workbook) {
        for (ReportModelOutBo report : reportModelRespVoList) {
            XSSFSheet sheet = workbook.getSheet(report.getSheetCode());
            XSSFCell cell = ExcelUtil.getCellByCellCoordinates(report.getCellCoord(), sheet);
            cell.setCellValue(report.getFillValue());
        }
    }

    @Transactional
    private boolean saveUploadExcelIndo(XSSFWorkbook workbook) {
        try {
            List<ReportModelOutBo> targetIndexInfoBos = reportIndexModelRepository.queryCalculatedTargetIndexInfo();
            for (ReportModelOutBo index : targetIndexInfoBos) {
                XSSFSheet sheet = workbook.getSheet(index.getSheetCode());
                XSSFCell cell = ExcelUtil.getCellByCellCoordinates(index.getCellCoord(), sheet);
                // TODO 组装指标存数据 save t_bpp_report_source_model t_bpp_report_source_data_detail
                Double cellVal = ExcelUtil.getDoubleCellVal(cell);
                if (cellVal == null) {
                    continue;
                }
                index.setFillValue(cellVal);
            }
            reportIndexDataDetailService.addBathReportSourceDataDetail(new ArrayList<>(targetIndexInfoBos));
        } catch (RuntimeException e) {
            log.error("保存指标存数据异常：", e);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 已有数据校验
     *
     * @param workbook excel
     */
    private StringBuilder existingDataVerification(XSSFWorkbook workbook, ReportModelInBo reportModelInBo) {
        List<ReportModelOutBo> reportModelRespVoList = reportIndexModelRepository.queryMonthlyModelRecord(reportModelInBo);
        // 已有数据校验
        StringBuilder errorInfo = new StringBuilder();
        for (ReportModelOutBo report : reportModelRespVoList) {
            XSSFSheet sheet = workbook.getSheet(report.getSheetCode()); // sheet
            XSSFCell cell = ExcelUtil.getCellByCellCoordinates(report.getCellCoord(), sheet);
            Double cellVal = ExcelUtil.getDoubleCellVal(cell);
            if (cellVal == null || report.getFillValue().compareTo(cellVal) != 0) {
                errorInfo.append("[").append(report.getSheetCode()).append("]sheet页,")
                        .append(report.getCellCoord()).append("单元格数据异常;");
            }
        }
        return errorInfo;
    }


}
