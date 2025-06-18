package com.example.bpp.salesflow.core;

public interface Rule<T extends BusinessEntity> {
    boolean evaluate(T entity);

    int getPriority();

    String getId();

    boolean isApplicable(T entity);
}
