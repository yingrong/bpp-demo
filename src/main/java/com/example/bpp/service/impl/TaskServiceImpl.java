package com.example.bpp.service.impl;

import com.example.bpp.bo.CalculatedTaskBo;
import com.example.bpp.bo.ReportModelInBo;
import com.example.bpp.bo.ReportModelOutBo;
import com.example.bpp.entity.DepIndexConfPo;
import com.example.bpp.entity.ReportIndexDataDetailPo;
import com.example.bpp.metric.Metric;
import com.example.bpp.metric.formula.Exp4JFormula;
import com.example.bpp.metric.source.Exp4jMapParamSource;
import com.example.bpp.repository.DepIndexConfRepository;
import com.example.bpp.repository.ReportIndexDataDetailRepository;
import com.example.bpp.repository.ReportIndexModelRepository;
import com.example.bpp.service.ReportIndexDataDetailService;
import com.example.bpp.service.TaskService;
import com.example.bpp.util.DagSort;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author xuefei
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private ReportIndexDataDetailService reportIndexDataDetailService;
    @Resource
    private ReportIndexModelRepository reportIndexModelRepository;
    @Resource
    private DepIndexConfRepository depIndexConfRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public CalculatedTaskBo queryIndexCalculatedData() {
        // 获取需要计算指标
        List<ReportModelOutBo> targetIndexInfoBos = reportIndexModelRepository.queryCalculatedTargetIndexInfo();
        Map<String, ReportModelOutBo> calculatedIndexMap = targetIndexInfoBos.stream().collect(Collectors.toMap(ReportModelOutBo::getTargetIndex, (v) -> v));
        // 获取所有指标数据
        List<DepIndexConfPo> depIndexConfPos = depIndexConfRepository.queryDependentIndexConf();
        // 目标指标与来源指标关系map
        Map<String, List<String>> depIndexConfMap = depIndexConfPos.stream().collect(Collectors.groupingBy(DepIndexConfPo::getTargetIndex,
                Collectors.mapping(DepIndexConfPo::getSourceIndex, Collectors.toList())));
        Set<String> vertexSet = new HashSet<>();
        for (DepIndexConfPo depIndexConfPo : depIndexConfPos) {
            vertexSet.add(depIndexConfPo.getTargetIndex());
            vertexSet.add(depIndexConfPo.getSourceIndex());
        }
        // 对数据进行拓扑排序
        List<String> dagList = DagSort.sort(vertexSet.stream().toList(), depIndexConfMap);
        if (dagList == null) {
            return null;
        }
        // 获取指标对应需计算指标
        ReportModelInBo reportModelInBo = new ReportModelInBo();
        reportModelInBo.setTargetIndexList(calculatedIndexMap.keySet());
        List<ReportModelOutBo> reportModelOutBos = reportIndexModelRepository.queryCalculatedBasicInfoByTargetIndex(reportModelInBo);
        Map<String, List<ReportModelOutBo>> sourceMap = reportModelOutBos.stream().collect(Collectors.groupingBy(ReportModelOutBo::getTargetIndex));
        return new CalculatedTaskBo().setIndexes(dagList)
                .setCalculatedIndexMap(calculatedIndexMap)
                .setSourceMap(sourceMap);
    }

    @Transactional
    @Override
    public void indexCalculatedDataSave(CalculatedTaskBo calculatedTaskBo) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String targetIndex = calculatedTaskBo.getTargetIndex();
            if (StringUtils.isBlank(targetIndex)) {
                return;
            }
            Map<String, ReportModelOutBo> calculatedIndexMap = calculatedTaskBo.getCalculatedIndexMap();
            if (!calculatedIndexMap.containsKey(targetIndex)) {
                return;
            }
            ReportModelOutBo calculatedIndexBo = calculatedIndexMap.get(targetIndex);
            // 获取指标计算值
            getIndexCalculatedVal(targetIndex, calculatedTaskBo.getSourceMap(), calculatedIndexBo, calculatedIndexMap);
            reportIndexDataDetailService.addReportSourceDataDetail(calculatedIndexMap.get(targetIndex));
            transactionManager.commit(status);
        } catch (Exception e) {
            status.setRollbackOnly();
            throw e;
        }
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
    private static void getIndexCalculatedVal(String targetIndex, Map<String, List<ReportModelOutBo>> sourceMap, ReportModelOutBo calculatedIndexBo, Map<String, ReportModelOutBo> calculatedIndexMap) {
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
        calculatedIndexBo.setFillValue(value);
        calculatedIndexMap.put(targetIndex, calculatedIndexBo);
    }
}
