package com.example.bpp.metric;

import java.math.BigDecimal;

public class FormulaResult<T> {

    private T t;

    private boolean success;
    boolean success(){
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setT(T t) {
        this.t = t;
    }

    T getValue() {
        return t;
    }
}
