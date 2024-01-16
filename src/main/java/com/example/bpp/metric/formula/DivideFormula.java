package com.example.bpp.metric.formula;

import com.example.bpp.metric.Formula;
import com.example.bpp.metric.FormulaResult;
import com.example.bpp.metric.param.TwoDoubleParam;

public class DivideFormula implements Formula<TwoDoubleParam, Double> {

    @Override
    public FormulaResult<Double> evaluate(TwoDoubleParam data) {

        double v = data.getD1() / data.getD2();
        FormulaResult<Double> formulaResult = new FormulaResult<>();

        formulaResult.setSuccess(true);
        formulaResult.setT(v);
        return formulaResult;
    }
}
