package hu.bme.jschneid.dijkstra;

public class NodeInfo {

    private Integer nodeId;
    private Long distance;
    private Integer prevNode;


    public NodeInfo(Integer nodeId, Long distance) {
        this.nodeId = nodeId;
        this.distance = distance;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Integer getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Integer prevNode) {
        this.prevNode = prevNode;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "nodeId=" + nodeId +
                ", distance=" + distance +
                ", prevNode=" + prevNode +
                '}';
    }
}
