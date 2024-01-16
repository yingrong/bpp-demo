package com.example.bpp.task;

import java.util.Collection;
import java.util.Set;

public final class DAG<V, E> implements Digraph<V, E> {
    private final LinkedHashSetMultimap outDegree;
    private final LinkedHashSetMultimap inDegree;

    public DAG(final LinkedHashSetMultimap outDegree, final LinkedHashSetMultimap inDegree) {
        this.outDegree = outDegree;
        this.inDegree = inDegree;
    }

    public static DAG create() {
        return new DAG(new LinkedHashSetMultimap(), new LinkedHashSetMultimap());
    }

    @Override
    public E get(Object source, Object target) {
        return null;
    }

    @Override
    public boolean contains(Object source, Object target) {
        return false;
    }

    @Override
    public boolean contains(Object vertex) {
        return false;
    }

    @Override
    public boolean add(V vertex) {
        outDegree.put(vertex, null);
        inDegree.put(vertex, null);
        return true;
    }

    @Override
    public E put(V source, V target, E edge) {

        return null;
    }

    @Override
    public E remove(V source, V target) {
        return null;
    }

    @Override
    public boolean remove(V vertex) {
        return false;
    }

    @Override
    public void removeAll(Collection<V> vertices) {

    }

    @Override
    public Iterable<V> vertices() {
        return null;
    }

    @Override
    public Iterable<V> targets(Object source) {
        return null;
    }

    @Override
    public int getVertexCount() {
        return 0;
    }

    @Override
    public int getOutDegree(Object vertex) {
        return 0;
    }

    @Override
    public int getEdgeCount() {
        return 0;
    }

    @Override
    public boolean isAcyclic() {
        return false;
    }

    @Override
    public Digraph<V, E> reverse() {
        return null;
    }

    @Override
    public Digraph<V, E> subgraph(Set<V> vertices) {
        return null;
    }
}
