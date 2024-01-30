package com.example.bpp.metric;

import com.example.bpp.metric.formula.AddFormula;
import com.example.bpp.metric.formula.DivideFormula;
import com.example.bpp.metric.formula.Exp4JFormula;
import com.example.bpp.metric.formula.MinusFormula;
import com.example.bpp.metric.param.AddParam;
import com.example.bpp.metric.param.Exp4jMapParam;
import com.example.bpp.metric.param.TwoDoubleParam;
import com.example.bpp.metric.source.AddSource;
import com.example.bpp.metric.source.Exp4jMapParamSource;
import com.example.bpp.metric.source.TwoDoubleSource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MetricTest {

    @Test
    void metric_evaluate_add() {
        Metric<AddParam, BigDecimal> metric = new Metric<>();

        metric.setName("add");
        metric.setFormula(new AddFormula());
        metric.setParamSource(new AddSource());

        FormulaResult<BigDecimal> evaluate = metric.evaluate();

        assertTrue(evaluate.success());
        assertEquals(BigDecimal.valueOf(6L), evaluate.getValue());
    }


    @Test
    void metric_evaluate_divide() {
        Metric<TwoDoubleParam, Double> metric = new Metric<>("divide", new DivideFormula(), new TwoDoubleSource());

        FormulaResult<Double> formulaResult = metric.evaluate();

        assertTrue(formulaResult.success());

        assertEquals(3, (double) formulaResult.getValue(), 0.1);
    }

    @Test
    void metric_evaluate_minus() {
        Metric<TwoDoubleParam, Double> metric = new Metric<>("minus", new MinusFormula(), new TwoDoubleSource());

        FormulaResult<Double> formulaResult = metric.evaluate();

        assertTrue(formulaResult.success());

        assertEquals(2.2, (double) formulaResult.getValue(), 0.1);
    }


    @Test
    void metric_evaluate_exp4j() {
        Exp4JFormula formula = new Exp4JFormula();
        formula.setExpressionString("x - y");
        formula.setParams(List.of("x", "y"));

        Metric<Exp4jMapParam, Double> metric = new Metric<>("exp4j", formula, new Exp4jMapParamSource());

        FormulaResult<Double> formulaResult = metric.evaluate();

        assertTrue(formulaResult.success());

        assertEquals(2.2, (double) formulaResult.getValue(), 0.1);
    }
}