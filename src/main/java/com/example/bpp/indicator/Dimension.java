package com.example.bpp.indicator;

import java.util.List;

public class Dimension {
    private String type;
    private List<String> values;
    private int order;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
