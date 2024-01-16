package com.example.bpp.task;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;


public class TaskTest {
    public static void main(String[] args) {
        MutableGraph<MetricTask> mutableGraph = GraphBuilder.directed().allowsSelfLoops(false).build();

        MetricTask task1 = new MetricTask();
        MetricTask task2 = new MetricTask();
        MetricTask task3 = new MetricTask();
        MetricTask task4 = new MetricTask();
        MetricTask task5 = new MetricTask();
        MetricTask task6 = new MetricTask();
        MetricTask task7 = new MetricTask();
        MetricTask task8 = new MetricTask();

        mutableGraph.putEdge(task1, task2);
        mutableGraph.putEdge(task1, task3);
        mutableGraph.putEdge(task3, task5);
        mutableGraph.putEdge(task4, task5);
        mutableGraph.putEdge(task2, task7);
        mutableGraph.putEdge(task6, task8);
        mutableGraph.putEdge(task5, task1);

        System.out.println(mutableGraph);

        int size = mutableGraph.nodes().size();
        System.out.println("nodes size: " + size);
        int size1 = mutableGraph.edges().size();
        System.out.println("edges size: " + size1);

        System.out.println(Graphs.hasCycle(mutableGraph));

        mutableGraph.edges().forEach(metricTasks -> {
            System.out.println(metricTasks.source() + " -> " + metricTasks.target());

        });
//        System.out.println(format(mutableGraph.nodes()));
    }
}
