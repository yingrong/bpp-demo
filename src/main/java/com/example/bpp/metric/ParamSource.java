package com.example.bpp.metric;

import com.example.bpp.metric.FormulaParam;

public interface ParamSource<T extends FormulaParam> {
    T getData();
}
