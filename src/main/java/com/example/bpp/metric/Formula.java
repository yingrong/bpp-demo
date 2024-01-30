package com.example.bpp.metric;

public interface Formula<T extends FormulaParam,V>  {
    FormulaResult<V> evaluate(T formulaParam);
}
