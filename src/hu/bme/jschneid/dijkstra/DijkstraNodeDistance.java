package hu.bme.jschneid.dijkstra;

import hu.bme.jschneid.common.Node;

public class DijkstraNodeDistance {

    private Node node;
    private Long distance;
    private Node prevNode;


    public DijkstraNodeDistance(Node node, Long distance) {
        this.node = node;
        this.distance = distance;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node prevNode) {
        this.prevNode = prevNode;
    }

    @Override
    public String toString() {
        return "DijkstraNodeDistance{" +
                "node=" + node +
                ", distance=" + distance +
                ", prevNode=" + prevNode +
                '}';
    }
}
