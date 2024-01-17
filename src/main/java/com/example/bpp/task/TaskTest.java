package com.example.bpp.task;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;


public class TaskTest {
    public static void main(String[] args) {
        MutableGraph<MetricTask> graph = GraphBuilder.directed().allowsSelfLoops(false).build();

        MetricTask task1 = new MetricTask();
        MetricTask task2 = new MetricTask();
        MetricTask task3 = new MetricTask();
        MetricTask task4 = new MetricTask();
        MetricTask task5 = new MetricTask();
        MetricTask task6 = new MetricTask();
        MetricTask task7 = new MetricTask();
        MetricTask task8 = new MetricTask();

        graph.putEdge(task1, task2);
        graph.putEdge(task1, task3);
        graph.putEdge(task3, task5);
        graph.putEdge(task4, task5);
        graph.putEdge(task2, task7);
        graph.putEdge(task6, task8);
        graph.putEdge(task5, task1);

        System.out.println(graph);

        int size = graph.nodes().size();
        System.out.println("nodes size: " + size);
        int size1 = graph.edges().size();
        System.out.println("edges size: " + size1);

        System.out.println(Graphs.hasCycle(graph));

        graph.edges().forEach(metricTasks -> System.out.println(metricTasks.source() + " -> " + metricTasks.target()));

        topologyTraverse(graph, metricTask -> {
            if (metricTask.isDone()) {
                return true;
            }
            return metricTask.execute();
        });


    }


    public static <T> void topologyTraverse(Graph<T> graph, Function<T, Boolean> function) {
        // 1. 拓扑排序需要的辅助队列。
        Queue<T> queue = new LinkedList<>();

        // 2. 将root节点(入度为0的节点)入队列。
        graph.nodes().stream()
                .filter(task -> graph.inDegree(task) == 0)
                .forEach(queue::add);

        // 3. 非root节点对应的入度记录表。
        Map<T, Integer> taskIntegerMap = graph.nodes().stream()
                .filter(task -> graph.inDegree(task) != 0)
                .collect(Collectors.toMap(node -> node, graph::inDegree));

        // 4. 拓扑遍历
        while (!queue.isEmpty()) {
            // 5. 从队列取节点。
            T current = queue.poll();

            // 6. 执行function逻辑。
            Boolean result = function.apply(current);

            // 7. 拓扑排序, 将当前节点的后继节点入度减一, 如果入度为0, 则加入队列, 等待调度。
            if (result) {
                graph.successors(current).forEach(node -> {
                    Integer i = taskIntegerMap.get(node);
                    if (--i == 0) {
                        taskIntegerMap.remove(node);
                        queue.offer(node);
                    } else {
                        taskIntegerMap.put(node, i);
                    }
                });
            }
        }
    }

}
