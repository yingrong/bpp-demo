package com.example.bpp.metric;

import com.example.bpp.metric.formula.AddFormula;
import com.example.bpp.metric.formula.DivideFormula;
import com.example.bpp.metric.formula.Exp4JFormula;
import com.example.bpp.metric.formula.MinusFormula;
import com.example.bpp.metric.source.AddSource;
import com.example.bpp.metric.source.Exp4jMapParamSource;
import com.example.bpp.metric.source.TwoDoubleSource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MetricTest {

    @Test
    void metric_evaluate_add() {
        Metric metric = new Metric();

        metric.setName("add");
        metric.setFormula(new AddFormula());
        metric.setParamSource(new AddSource());

        FormulaResult evaluate = metric.evaluate();

        assertTrue(evaluate.success());
        assertEquals(BigDecimal.valueOf(6L), evaluate.getValue());
    }


    @Test
    void metric_evaluate_divide() {
        Metric metric = new Metric("divide", new DivideFormula(), new TwoDoubleSource());

        FormulaResult formulaResult = metric.evaluate();

        assertTrue(formulaResult.success());

        assertEquals(3, (double) formulaResult.getValue(), 0.1);
    }

    @Test
    void metric_evaluate_minus() {
        Metric metric = new Metric("minus", new MinusFormula(), new TwoDoubleSource());

        FormulaResult formulaResult = metric.evaluate();

        assertTrue(formulaResult.success());

        assertEquals(2.2, (double) formulaResult.getValue(), 0.1);
    }


    @Test
    void metric_evaluate_exp4j() {
        Exp4JFormula formula = new Exp4JFormula();
        formula.setExpression("(f1 - f2)/f3");
        formula.setParams(List.of("f1", "f2", "f3"));
        Map<String, Double> paramMap = new HashMap<>();
        paramMap.put("f1", 10.9);
        paramMap.put("f2", 1d);
        paramMap.put("f3", 3d);
//        Metric metric = new Metric("exp4j", formula, (ParamSource) new Exp4jMapParamSource().getData(paramMap));
        Metric metric = new Metric();
        metric.setName("exp4j");
        metric.setFormula(formula);
        metric.setParamSource(new Exp4jMapParamSource());

        FormulaResult formulaResult = metric.evaluate(paramMap);
        System.out.println(formulaResult.getValue());

        assertTrue(formulaResult.success());

        assertEquals(3.3, (double) formulaResult.getValue(), 0.1);
    }

    @Test
    void metric_evaluate_t() {
        System.out.println(Double.NaN);
    }
}