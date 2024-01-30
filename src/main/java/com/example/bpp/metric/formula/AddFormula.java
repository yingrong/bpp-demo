package com.example.bpp.metric.formula;

import com.example.bpp.metric.param.AddParam;
import com.example.bpp.metric.Formula;
import com.example.bpp.metric.FormulaResult;

import java.math.BigDecimal;

public class AddFormula implements Formula<AddParam, BigDecimal> {


    @Override
    public FormulaResult<BigDecimal> evaluate(AddParam data) {

        BigDecimal sum = data.getNumberList().stream()
                .reduce(BigDecimal::add)
                .get();

        FormulaResult<BigDecimal> formulaResult = new FormulaResult<>();

        formulaResult.setSuccess(true);
        formulaResult.setT(sum);
        return formulaResult;
    }
}
