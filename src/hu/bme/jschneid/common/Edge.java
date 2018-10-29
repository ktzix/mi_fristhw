package hu.bme.jschneid.common;

import java.util.Objects;

public class Edge<T> {

    private Node<T> node;
    private Node<T> oppositeNode;
    private Long distance;

    public Edge(Node<T> nodeId, Node<T> oppositeNodeIid, Long distance) {
        this.node = nodeId;
        this.oppositeNode = oppositeNodeIid;
        this.distance = distance;
    }

    public Long getDistance() {
        return distance;
    }


    public Node<T> getNode() {
        return node;
    }

    public Node<T> getOppositeNode() {
        return oppositeNode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(node, edge.node) &&
                Objects.equals(oppositeNode, edge.oppositeNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, oppositeNode);
    }
}
