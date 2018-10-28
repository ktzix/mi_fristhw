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


    public Node getNode() {
        return node;
    }

    public Node getOppositeNode() {
        return oppositeNode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (Objects.equals(node, edge.node) &&
                Objects.equals(oppositeNode, edge.oppositeNode) )
||
        (Objects.equals(node, edge.oppositeNode) &&
                Objects.equals(oppositeNode, edge.node) )
        ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, oppositeNode) + Objects.hash(oppositeNode, node);
    }
}
