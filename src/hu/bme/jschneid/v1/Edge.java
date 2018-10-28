package hu.bme.jschneid.v1;

import hu.bme.jschneid.v1.Node;

public class Edge {

    Node a, b;

    public Edge(Node a, Node b) {
        this.a = a;
        this.b = b;
    }

    public Node getA() {
        return a;
    }

    public Node getB() {
        return b;
    }
}
