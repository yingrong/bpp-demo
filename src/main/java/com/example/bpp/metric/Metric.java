package com.example.bpp.metric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metric {

    private String name;

    private Formula formula;

    private ParamSource paramSource;

    public FormulaResult evaluate() {
        return formula.evaluate(paramSource.getData());

    }

    public FormulaResult evaluate(Map<String, Double> paramMap) {
        return formula.evaluate(paramSource.getData(paramMap));
    }

}
