package com.example.bpp.metric.param;

import com.example.bpp.metric.FormulaParam;

import java.math.BigDecimal;
import java.util.List;

public class AddParam implements FormulaParam {

    private List<BigDecimal> numberList;

    public List<BigDecimal> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<BigDecimal> numberList) {
        this.numberList = numberList;
    }
}
