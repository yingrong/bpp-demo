package com.example.bpp.metric.source;

import com.example.bpp.metric.ParamSource;
import com.example.bpp.metric.param.Exp4jMapParam;

import java.util.HashMap;
import java.util.Map;

public class Exp4jMapParamSource implements ParamSource<Exp4jMapParam> {

    private Map<String, Double> map = new HashMap<>();

    @Override
    public Exp4jMapParam getData() {
        Exp4jMapParam exp4jMapParam = new Exp4jMapParam();
        map.put("x", 3.3);
        map.put("y", 1.1);
        exp4jMapParam.setMap(map);
        return exp4jMapParam;
    }
}
