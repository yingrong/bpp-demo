package com.example.bpp.metric.param;

import com.example.bpp.metric.FormulaParam;

public class TwoDoubleParam implements FormulaParam {

    private double d1;
    private double d2;

    public TwoDoubleParam(double d1, double d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    public double getD1() {
        return d1;
    }

    public double getD2() {
        return d2;
    }
}
