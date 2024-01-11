package com.example.bpp.metric;

public class Metric {

    private String name;

    private Formula formula;

    private ParamSource paramSource;

    public Metric() {
    }

    public Metric(String name, Formula formula, ParamSource paramSource) {
        this.name = name;
        this.formula = formula;
        this.paramSource = paramSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    public ParamSource getParamSource() {
        return paramSource;
    }

    public void setParamSource(ParamSource paramSource) {
        this.paramSource = paramSource;
    }

    public FormulaResult evaluate() {

        FormulaResult evaluate = formula.evaluate(paramSource.getData());

        return evaluate;

    }

}
