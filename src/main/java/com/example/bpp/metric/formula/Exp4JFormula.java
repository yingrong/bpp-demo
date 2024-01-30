package com.example.bpp.metric.formula;

import com.example.bpp.metric.param.Exp4jMapParam;
import com.example.bpp.metric.Formula;
import com.example.bpp.metric.FormulaResult;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.List;

public class Exp4JFormula implements Formula<Exp4jMapParam, Double> {

    private String expressionString;
    private List<String> params;

    public void setExpressionString(String expressionString) {
        this.expressionString = expressionString;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    @Override
    public FormulaResult<Double> evaluate(Exp4jMapParam data) {

        Expression expression = buildExpression(data);

        FormulaResult<Double> formulaResult = new FormulaResult<>();
        formulaResult.setSuccess(true);
        formulaResult.setT(expression.evaluate());
        return formulaResult;
    }

    private Expression buildExpression(Exp4jMapParam data) {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder(expressionString);
        params.forEach(expressionBuilder::variable);
        Expression expression = expressionBuilder.build();
        data.getMap().forEach(expression::setVariable);
        return expression;
    }
}
