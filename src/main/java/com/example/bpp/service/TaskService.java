package com.example.bpp.service;

import com.example.bpp.bo.CalculatedTaskBo;
import com.example.bpp.bo.ReportModelOutBo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface TaskService {
    /**
     * 获取指标计算数据
     */
    CalculatedTaskBo queryIndexCalculatedData();

    /**
     * 计算保存指标数据
     * @param sortedNodes 排序后指标数据
     * @param calculatedIndexMap 计算指标map
     * @param sourceMap 指标来源数据map
     */
    void indexCalculatedDataSave(CalculatedTaskBo calculatedTaskBo);
}