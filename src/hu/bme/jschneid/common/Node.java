package hu.bme.jschneid.common;

import java.util.Objects;

public class Node<T> {

    private Integer id;
    private String tag;
    private T payload;

    public Node(Integer id) {
        this.id = id;
    }

    public Node(Integer id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public Node(Integer id, String tag,T payload) {
        this.id = id;
        this.tag = tag;
        this.payload = payload;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
