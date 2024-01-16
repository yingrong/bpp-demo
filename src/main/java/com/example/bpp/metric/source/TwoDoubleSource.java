package com.example.bpp.metric.source;

import com.example.bpp.metric.ParamSource;
import com.example.bpp.metric.param.TwoDoubleParam;

import java.util.Map;

public class TwoDoubleSource implements ParamSource<TwoDoubleParam> {
    @Override
    public TwoDoubleParam getData() {
        return new TwoDoubleParam(3.3,1.1);
    }

    @Override
    public TwoDoubleParam getData(Map<String, Double> paramMap) {
        return new TwoDoubleParam(paramMap.get(0), paramMap.get(1));
    }
}
