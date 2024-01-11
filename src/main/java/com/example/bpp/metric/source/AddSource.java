package com.example.bpp.metric.source;

import com.example.bpp.metric.ParamSource;
import com.example.bpp.metric.param.AddParam;

import java.math.BigDecimal;
import java.util.List;

public class AddSource implements ParamSource<AddParam> {

    @Override
    public AddParam getData() {
        AddParam addParam = new AddParam();
        addParam.setNumberList(List.of(BigDecimal.ONE, BigDecimal.TWO, BigDecimal.valueOf(3)));
        return addParam;
    }
}
