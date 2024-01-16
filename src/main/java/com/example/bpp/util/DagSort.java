package com.example.bpp.util;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.NotDirectedAcyclicGraphException;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.json.JSONObject;

import java.util.*;

@Slf4j
public class DagSort {
    public static List<String> sort(List<String> vertexes, Map<String, List<String>> map) {
        // 计算每个节点的入度
        DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        vertexes.forEach(graph::addVertex);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String vertex = entry.getKey();
            List<String> edges = entry.getValue();
            for (String edge : edges) {
                if (graph.containsEdge(vertex, edge)) {
                    log.error("[{}, {}]数据已存在", vertex, edge);
                    return null;
                }
                graph.addEdge(vertex, edge);
            }
        }
        TopologicalOrderIterator<String, DefaultEdge> iterator = new TopologicalOrderIterator<>(graph);
        ImmutableList<String> topologicalOrder = null;
        try {
            topologicalOrder = ImmutableList.copyOf(iterator);
        } catch (NotDirectedAcyclicGraphException e) {
            log.error("[{}]非DAG数据", JSONObject.valueToString(map), e);
            return null;
        }
        return topologicalOrder.reverse();
    }

    public static void main(String[] args) {
        // 创建DAG数据结构
        Map<String, List<String>> map = new HashMap<>();
        map.put("A1", Arrays.asList("A2", "A5", "A6"));
//        map.put("A6", Arrays.asList("A2", "A2"));
        map.put("A6", Arrays.asList("A2"));
        map.put("A2", Arrays.asList("A3", "A4"));

        List<String> sort = sort(List.of("A1", "A2", "A3", "A4", "A5", "A6"), map);
        // 打印排序结果
        System.out.println(JSONObject.valueToString(sort));
    }
}