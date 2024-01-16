package com.example.bpp.metric;

import com.example.bpp.metric.FormulaParam;

import java.util.Map;

public interface ParamSource<T extends FormulaParam> {
    T getData();
    T getData(Map<String, Double> paramMap);
}
