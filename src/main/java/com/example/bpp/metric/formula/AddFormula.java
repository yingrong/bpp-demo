package com.example.bpp.metric.formula;

import com.example.bpp.metric.param.AddParam;
import com.example.bpp.metric.Formula;
import com.example.bpp.metric.FormulaResult;

import java.math.BigDecimal;

public class AddFormula implements Formula<AddParam> {


    @Override
    public FormulaResult evaluate(AddParam data) {

        BigDecimal sum = data.getNumberList().stream()
                .reduce((number, number2) -> number.add(number2))
                .get();

        FormulaResult formulaResult = new FormulaResult() {
            @Override
            public boolean success() {
                return true;
            }

        };

        formulaResult.setT(sum);
        return formulaResult;
    }
}
