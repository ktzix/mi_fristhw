package hu.bme.jschneid.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Graph<T, V> {

    private Set<Node<T>> nodes = new HashSet<>();
    private Set<Edge<T>> edges = new HashSet<>();
    private V payload;

    public Graph() {
    }

    public Graph(V payload) {
        this.payload = payload;
    }

    public void addNode(Node<T> node) {
        this.nodes.add(node);
    }

    public void addEdge(Node<T> node, Node<T> oppositeNode, Long distance) {
        this.addEdge(new Edge<>(node, oppositeNode, distance));
    }

    public void addEdge(Edge<T> edge) {
        this.edges.add(edge);
    }

    public List<Edge> getEdges(Node node) {
        List<Edge> edgesByNode = new ArrayList<>();
        for (Edge edge : edges) {
            if ( edge.getNode().equals(node)) {
                edgesByNode.add(edge);
            }
        }
        return edgesByNode;
    }

    public Node<T> getNodeByTag(String tag) {
        for (Node<T> node :   nodes) {
            if (tag.equals(node.getTag())) {
                return node;
            }
        }
        return null;
    }

    public Node<T> getNodeById(Integer id) {
        for (Node<T> node :   nodes) {
            if (id.equals(node.getId())) {
                return node;
            }
        }
        return null;
    }

    public Set<Node<T>> getNodes() {
        return nodes;
    }

    public Set<Edge<T>> getEdges() {
        return edges;
    }

    private boolean isEdgeContainsNode(Edge edge, Node node) {
        if (edge == null || node == null) {
            return false;
        }
        return node.equals(edge.getNode()) || node.equals(edge.getOppositeNode());
    }

    public List<Node > getChildren(Node node){
        return getEdges(node).stream().map((Function<Edge, Node>) Edge::getOppositeNode).collect(Collectors.toList());
    }

    public V getPayload() {
        return payload;
    }
}
