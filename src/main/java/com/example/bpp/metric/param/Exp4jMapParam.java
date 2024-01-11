package com.example.bpp.metric.param;

import com.example.bpp.metric.FormulaParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Exp4jMapParam implements FormulaParam {

    private Map<String, Double> map;

    public Map<String, Double> getMap() {
        return map;
    }

    public void setMap(Map<String, Double> map) {
        this.map = map;
    }
}
