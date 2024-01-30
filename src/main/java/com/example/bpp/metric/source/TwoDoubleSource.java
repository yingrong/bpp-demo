package com.example.bpp.metric.source;

import com.example.bpp.metric.ParamSource;
import com.example.bpp.metric.param.TwoDoubleParam;

public class TwoDoubleSource implements ParamSource<TwoDoubleParam> {
    @Override
    public TwoDoubleParam getData() {
        return new TwoDoubleParam(3.3,1.1);
    }
}
