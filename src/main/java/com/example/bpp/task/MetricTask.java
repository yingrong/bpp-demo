package com.example.bpp.task;

import com.example.bpp.metric.Metric;

public class MetricTask {

    private Metric metric;

    private boolean done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    public boolean execute() {
        return true;
    }

}
