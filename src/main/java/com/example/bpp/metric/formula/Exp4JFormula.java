package com.example.bpp.metric.formula;

import com.example.bpp.metric.param.Exp4jMapParam;
import com.example.bpp.metric.Formula;
import com.example.bpp.metric.FormulaResult;
import lombok.Data;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.List;

@Data
public class Exp4JFormula implements Formula<Exp4jMapParam> {

    private String expression;
    private List<String> params;

    @Override
    public FormulaResult evaluate(Exp4jMapParam data) {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(expression);
        for (String param : params) {
            expressionBuilder.variable(param);
        }
        Expression build = expressionBuilder.build();
        data.getMap().forEach(build::setVariable);
        FormulaResult<Double> formulaResult = new FormulaResult<>();
        formulaResult.setSuccess(true);
        formulaResult.setT(build.evaluate());
        return formulaResult;
    }
}
