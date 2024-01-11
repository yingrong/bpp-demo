package com.example.bpp.metric;

public interface Formula<T extends FormulaParam>  {
    FormulaResult evaluate(T data);
}
