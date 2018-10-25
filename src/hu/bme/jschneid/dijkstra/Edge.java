package hu.bme.jschneid.dijkstra;

import java.util.Objects;

public class Edge {

    private Integer nodeId;
    private Integer oppositeNodeIid;
    private Long distance;

    public Edge(Integer nodeId, Integer oppositeNodeIid, Long distance) {
        this.nodeId = nodeId;
        this.oppositeNodeIid = oppositeNodeIid;
        this.distance = distance;
    }


    public Integer getOppositeNodeIid() {
        return oppositeNodeIid;
    }

    public void setOppositeNodeIid(Integer oppositeNodeIid) {
        this.oppositeNodeIid = oppositeNodeIid;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(nodeId, edge.nodeId) &&
                Objects.equals(oppositeNodeIid, edge.oppositeNodeIid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, oppositeNodeIid);
    }
}
