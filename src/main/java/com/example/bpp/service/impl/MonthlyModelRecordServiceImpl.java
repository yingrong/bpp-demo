package com.example.bpp.service.impl;

import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.bo.ReportModelOutBo;
import com.example.bpp.entity.DepIndexConfPo;
import com.example.bpp.entity.ReportSourceDataDetailPo;
import com.example.bpp.metric.Metric;
import com.example.bpp.metric.formula.Exp4JFormula;
import com.example.bpp.metric.source.Exp4jMapParamSource;
import com.example.bpp.repository.DepIndexConfRepository;
import com.example.bpp.repository.ReportSourceDataDetailRepository;
import com.example.bpp.repository.ReportSourceModelRepository;
import com.example.bpp.service.MonthlyModelRecordService;
import com.example.bpp.util.ExcelUtil;
import com.example.bpp.util.TopologicalSortUtil;
import com.example.bpp.vo.ReportUploadAndDownloadReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author xuefei
 */
@Slf4j
@Service
public class MonthlyModelRecordServiceImpl implements MonthlyModelRecordService {

    @Resource
    private ReportSourceDataDetailRepository reportSourceDataDetailRepository;
    @Resource
    private ReportSourceModelRepository reportSourceModelRepository;
    @Resource
    private DepIndexConfRepository depIndexConfRepository;

    @Override
    @Transactional
    public void createMonthlyModelRecord() {
        List<ReportModelOutBo> reportModelRespVoList = reportSourceModelRepository.queryMonthlyModelRecord();
        XSSFWorkbook workbook = null;
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            File file = new File("E://新建文件夹//FILEC04001-经销店月结表_2024-01_65432_经销商名称-样例.xlsx");
            fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            exportExcelDataProcess(reportModelRespVoList, workbook);
            File output = new File("E://" + System.currentTimeMillis() + ".xlsx");
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

    private static void exportExcelDataProcess(List<ReportModelOutBo> reportModelRespVoList, XSSFWorkbook workbook) {
        for (ReportModelOutBo report : reportModelRespVoList) {
            XSSFSheet sheet = workbook.getSheet(report.getSheetCode()); // sheet
            XSSFCell cell = getXssfCellByCellCoordinates(report.getCellCoord(), sheet);
            cell.setCellValue(report.getFillValue());
        }
    }

    private static XSSFCell getXssfCellByCellCoordinates(String cellCoordinates, XSSFSheet sheet) {
        StringBuilder rowNum = new StringBuilder();
        StringBuilder stringBuffer = new StringBuilder();
        //  \d 为正则表达式表示[0-9]数字
        String[] eng = cellCoordinates.split("\\d");
        // \D 为正则表达式表示非数字
        String[] num = cellCoordinates.split("\\D");
        for (String s : eng) {
            stringBuffer.append(s);
        }
        for (String s : num) {
            rowNum.append(s);
        }
        int cellNum = ExcelUtil.letterToNumber(stringBuffer.toString());
        XSSFRow row = sheet.getRow(Integer.parseInt(rowNum.toString()) - 1);
        XSSFCell cell = row.getCell(cellNum - 1);
        return cell;
    }

    @Override
    @Transactional
    public void uploadMonthlyModelRecord(ReportUploadAndDownloadReqVo reqVo) {
        // TODO 按年份查询 返回月份组装指标数据

        MultipartFile file = reqVo.getFile();
        XSSFWorkbook workbook = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = file.getInputStream();
            workbook = new XSSFWorkbook(inputStream);
            StringBuilder errorInfo = existingDataVerification(workbook);
            if (StringUtils.isNotBlank(errorInfo)) {
                return;
            }
            // 保存上传指标数据
            boolean saveTag = saveUploadExcelIndo(workbook);
            // 获取最新数据计算指标
            List<ReportModelOutBo> indexReportModelRespVoList = queryMonthlyModelRecord();
            exportExcelDataProcess(indexReportModelRespVoList, workbook);
            File output = new File("E://" + System.currentTimeMillis() + ".xlsx");
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
        return;
    }

    private boolean saveUploadExcelIndo(XSSFWorkbook workbook) {
        List<ReportModelOutBo> targetIndexInfoBos = reportSourceModelRepository.queryCalculatedTargetIndexInfo();
        for (ReportModelOutBo index : targetIndexInfoBos) {
            XSSFSheet sheet = workbook.getSheet(index.getSheetCode());
            XSSFCell cell = getXssfCellByCellCoordinates(index.getCellCoord(), sheet);
            // TODO 组装指标存数据 save t_bpp_report_source_model t_bpp_report_source_data_detail
            double cellVal = getDoubleCellVal(cell);
            index.setFillValue(cellVal);
        }
        addReportSourceDataDetail(new ArrayList<>(targetIndexInfoBos));
        TransactionAspectSupport.currentTransactionStatus().flush();
        return Boolean.TRUE;
    }

    /**
     * 已有数据校验
     *
     * @param workbook excel
     */
    private StringBuilder existingDataVerification(XSSFWorkbook workbook) {
        List<ReportModelOutBo> reportModelRespVoList = reportSourceModelRepository.queryMonthlyModelRecord();
        // 已有数据校验
        StringBuilder errorInfo = new StringBuilder();
        for (ReportModelOutBo report : reportModelRespVoList) {
            XSSFSheet sheet = workbook.getSheet(report.getSheetCode()); // sheet
            XSSFCell cell = getXssfCellByCellCoordinates(report.getCellCoord(), sheet);
            double cellVal = getDoubleCellVal(cell);
            if (report.getFillValue().compareTo(cellVal) != 0) {
                errorInfo.append("[").append(report.getSheetCode()).append("]sheet页,")
                        .append(report.getCellCoord()).append("单元格数据异常;");
            }
        }
        return errorInfo;
    }

    private static double getDoubleCellVal(XSSFCell cell) {
        double cellVal = Double.parseDouble("0");
        if (CellType.NUMERIC.equals(cell.getCellType())) {
            cellVal = cell.getNumericCellValue();
        }
        if (CellType.STRING.equals(cell.getCellType())) {
            cellVal = Double.parseDouble(cell.getStringCellValue());
        }
        return cellVal;
    }

    public List<ReportModelOutBo> queryMonthlyModelRecord() {
        // 获取需要计算指标
        List<ReportModelOutBo> targetIndexInfoBos = reportSourceModelRepository.queryCalculatedTargetIndexInfo();
        Map<String, ReportModelOutBo> calculatedIndexMap = targetIndexInfoBos.stream().collect(Collectors.toMap(ReportModelOutBo::getTargetIndex, (v) -> v));
        // 获取所有指标数据
        List<DepIndexConfPo> depIndexConfPos = depIndexConfRepository.queryDependentIndexConf();
        // 目标指标与来源指标关系map
        Map<String, List<String>> depIndexConfMap = depIndexConfPos.stream().collect(Collectors.groupingBy(DepIndexConfPo::getTargetIndex,
                Collectors.mapping(DepIndexConfPo::getSourceIndex, Collectors.toList())));
        // 对数据进行拓扑排序
        LinkedList<String> sortedNodes = TopologicalSortUtil.topologicalSort(depIndexConfMap);
        // 获取指标对应需计算指标
        ReportModelInBo reportModelInBo = new ReportModelInBo();
        reportModelInBo.setTargetIndexList(calculatedIndexMap.keySet());
        List<ReportModelOutBo> reportModelOutBos = reportSourceModelRepository.queryCalculatedBasicInfoByTargetIndex(reportModelInBo);
        Map<String, List<ReportModelOutBo>> sourceMap = reportModelOutBos.stream().collect(Collectors.groupingBy(ReportModelOutBo::getTargetIndex));
        for (String bo : sortedNodes) {
            if (!calculatedIndexMap.containsKey(bo)) {
                continue;
            }
            ReportModelOutBo calculatedIndexBo = calculatedIndexMap.get(bo);
            // 获取指标计算值
            Double value = getIndexCalculatedVal(bo, sourceMap, calculatedIndexBo, calculatedIndexMap);
            // 计算结果赋值
            calculatedIndexBo.setFillValue(value);
            calculatedIndexMap.put(bo, calculatedIndexBo);
        }
        addReportSourceDataDetail(new ArrayList<>(calculatedIndexMap.values()));

        List<ReportModelOutBo> returnList = reportSourceModelRepository.queryMonthlyModelRecord();
        returnList.addAll(calculatedIndexMap.values());
        return returnList;
    }

    private void addReportSourceDataDetail(List<ReportModelOutBo> list) {
        // TODO 数据保存至 t_bpp_report_source_data_detail
        List<ReportSourceDataDetailPo> saveList = new ArrayList<>();
        for (ReportModelOutBo bo : list) {
            ReportSourceDataDetailPo po = new ReportSourceDataDetailPo()
//                        .setReportYear()
//                        .setReportMonth()
//                        .setDealerCode()
                    .setSheetCode(bo.getSheetCode())
//                        .setModelMonth()
                    .setIndexName(bo.getTargetIndex())
//                        .setDimType()
//                        .setDimName()
                    .setFillValue(bo.getFillValue());
//                    .setUpdateCount(0);
            saveList.add(po);
        }
        reportSourceDataDetailRepository.saveBatch(saveList);
    }

    /**
     * 获取指标计算值
     *
     * @param targetIndex        目標指標
     * @param sourceMap          指标来源数据Map
     * @param calculatedIndexBo  计算指标
     * @param calculatedIndexMap 计算指标Map(计算后MAP做update，后期用于再次计算与excel回写)
     * @return 指标计算结果集
     */
    private static Double getIndexCalculatedVal(String targetIndex, Map<String, List<ReportModelOutBo>> sourceMap, ReportModelOutBo calculatedIndexBo, Map<String, ReportModelOutBo> calculatedIndexMap) {
        // 根据关联关系列表分层计算数据
        Double value;
        List<ReportModelOutBo> sourceBos = sourceMap.get(targetIndex);
        Exp4JFormula exp4JFormula = new Exp4JFormula();
        exp4JFormula.setExpression(calculatedIndexBo.getIndexFormula());
        exp4JFormula.setParams(sourceBos.stream().map(v -> "P" + v.getFormulaParam()).collect(Collectors.toList()));
        Metric metric = new Metric("exp4j", exp4JFormula, new Exp4jMapParamSource());
        Map<String, Double> metricSourceMap = new HashMap<>();
        if (calculatedIndexMap.containsKey(targetIndex) && sourceBos.getLast().getFillValue() == null) {
            // 计算后数据二次计算
            for (ReportModelOutBo source : sourceBos) {
                metricSourceMap.put("P" + source.getFormulaParam(), calculatedIndexMap.get(source.getDataSourceIndex()).getFillValue());
            }
        } else {
            //  初始数据
            metricSourceMap = sourceBos.stream().collect(Collectors.toMap(t -> "P" + t.getFormulaParam(), ReportModelOutBo::getFillValue));
        }
        value = (Double) metric.evaluate(metricSourceMap).getValue();
        return value;
    }


}
