package com.example.bpp.metric;

public class Metric<P extends FormulaParam, V> {

    private String name;

    private Formula<P,V> formula;

    private ParamSource<P> paramSource;

    public Metric() {
    }

    public Metric(String name, Formula<P,V> formula, ParamSource<P> paramSource) {
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

    public Formula<P,V> getFormula() {
        return formula;
    }

    public void setFormula(Formula<P,V> formula) {
        this.formula = formula;
    }

    public ParamSource<P> getParamSource() {
        return paramSource;
    }

    public void setParamSource(ParamSource<P> paramSource) {
        this.paramSource = paramSource;
    }

    public FormulaResult<V> evaluate() {

        return formula.evaluate(paramSource.getData());

    }

}
