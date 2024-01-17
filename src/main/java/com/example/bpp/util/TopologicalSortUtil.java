package com.example.bpp.util;

import java.util.*;

public class TopologicalSortUtil {
    public static LinkedList<String> topologicalSort(Map<String, List<String>> map) {
        // 计算每个节点的入度
        Map<String, Integer> inDegree = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String node = entry.getKey();
            List<String> dependencies = entry.getValue();
            inDegree.put(node, dependencies.size());
        }
        // 拓扑排序
        LinkedList<String> sortedNodes = sort(map, inDegree);
        return sortedNodes;
    }

    public static LinkedList<String> sort(Map<String, List<String>> map, Map<String, Integer> inDegree) {
        LinkedList<String> sortedNodes = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        // 从入度为0的节点开始遍历
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() != 0) {
                dfs(map, entry.getKey(), visited, sortedNodes);
            }
        }

        return sortedNodes;
    }

    public static void dfs(Map<String, List<String>> map, String node, Set<String> visited, List<String> sortedNodes) {
        visited.add(node);
        List<String> dependencies = map.get(node);
        if (dependencies != null) {
            for (String dependency : dependencies) {
                if (!visited.contains(dependency)) {
                    dfs(map, dependency, visited, sortedNodes);
                }
            }
        }
        sortedNodes.add(node);
    }

    public static void main(String[] args) {
        // 创建DAG数据结构
        Map<String, List<String>> map = new HashMap<>();
        map.put("A", Arrays.asList("A1", "A2"));
        map.put("C", Arrays.asList("A1", "A"));
        map.put("B", Arrays.asList("C"));

        LinkedList<String> sortedNodes = topologicalSort(map);
        // 打印排序结果
        System.out.println(sortedNodes);
    }
}