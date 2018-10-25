package hu.bme.jschneid.dijkstra;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra {


    private static final Long INFINITY = Long.MAX_VALUE;

    private Node start;
    private List<Node> nodes;
    private List<Edge> edges;

    private Map<Integer, NodeInfo> info;

    private Map<Integer, List<Edge>> adjacencyListMap;

    private Map<Integer, Node> nodeMap;

    private Set<Node> unvisited;

    public Dijkstra(List<Node> nodes, List<Edge> edges, Node start) {
        this.start = start;
        this.nodes = nodes;
        this.edges = edges;

        this.nodeMap = new HashMap<>();
        for (Node node : nodes) {
            nodeMap.put(node.getId(), node);
        }

        unvisited = new HashSet<>(nodes);

        prepareNodeInfo();
        prepareAdjacencyLists();
    }


    private void prepareNodeInfo() {
        info = new HashMap<>();
        for (Node node :
                nodes) {
            Long distance = start.getId().equals(node.getId()) ? 0 : INFINITY;
            info.put(node.getId(), new NodeInfo(node.getId(), distance));
        }
    }

    /**
     * create adjacency lists ( szomszédsági lista)
     */
    private void prepareAdjacencyLists() {
        adjacencyListMap = new HashMap<>();
        for (Edge edge : edges) {
            adjacencyListMap.putIfAbsent(edge.getNodeId(), new ArrayList<>());
            adjacencyListMap.get(edge.getNodeId()).add(edge);
        }
    }

    public void doDijkstra() {
        doDijkstra(start);
    }

    private void doDijkstra(Node node) {

        // remove from unvisited
        unvisited.remove(node);

        // get the list of neighbour edges
        List<Edge> neighbours = this.adjacencyListMap.getOrDefault(node.getId(), Collections.emptyList());

        // get the distance of the actual node from the staring node
        long currentDistance = info.get(node.getId()).getDistance();

        // calculate distance to neighbours
        for (Edge edge : neighbours) {
            NodeInfo oppositeNodeInfo = info.get(edge.getOppositeNodeIid());
            long savedOppositeNodeDistance = oppositeNodeInfo.getDistance();
            long actualOppositeNodeDistance = currentDistance + edge.getDistance();
            // if we have found a shorter node, update the info
            if (actualOppositeNodeDistance < savedOppositeNodeDistance) {
                oppositeNodeInfo.setDistance(actualOppositeNodeDistance);
                oppositeNodeInfo.setPrevNode(node.getId());
            }
        }


        // find neighbour with shortest way from the start point
        List<Integer> listOfOppositeNodeIds = neighbours.stream().map(Edge::getOppositeNodeIid).collect(Collectors.toList());
        List<NodeInfo> neighbourNodeInfos = info.values().stream()
                .filter(nodeInfo -> listOfOppositeNodeIds.contains(nodeInfo.getNodeId()))
                .filter(nodeInfo -> unvisited.contains(nodeMap.get(nodeInfo.getNodeId())))
                .sorted((o1, o2) -> (int) (o1.getDistance() - o2.getDistance()))
                .collect(Collectors.toList());


        Node oppositeNodeWithShortestDistanceFromStartPoint = null;
        // select shortest distance
        if (!neighbourNodeInfos.isEmpty()) {
            NodeInfo info = neighbourNodeInfos.get(0);
            oppositeNodeWithShortestDistanceFromStartPoint = nodeMap.get(info.getNodeId());
        }

        if (oppositeNodeWithShortestDistanceFromStartPoint != null) {
            doDijkstra(oppositeNodeWithShortestDistanceFromStartPoint);
        }

    }

    public Map<Integer, NodeInfo> getInfo() {
        return info;
    }

    public static void main(String[] args) {
        String[] nodeNames = {"a", "b", "c", "d", "e"};
        List<Node> nodes = new ArrayList<>();
        Map<String, Node> byTag = new HashMap<>();
        Map<Integer, Node> byId = new HashMap<>();
        int i = 0;
        for (String nodeName : nodeNames) {
            Node node = new Node(i, nodeName);
            nodes.add(node);
            i++;
            byTag.put(nodeName,node);
            byId.put(node.getId(),node);
        }

        List<Edge> edges = new ArrayList<>();

        createEdge(edges,byTag,"a","b",7);
        createEdge(edges,byTag,"a","c",3);

        createEdge(edges,byTag,"b","a",7);
        createEdge(edges,byTag,"b","c",1);
        createEdge(edges,byTag,"b","d",2);
        createEdge(edges,byTag,"b","e",6);

        createEdge(edges,byTag,"c","a",3);
        createEdge(edges,byTag,"c","b",1);
        createEdge(edges,byTag,"c","d",2);

        createEdge(edges,byTag,"d","c",2);
        createEdge(edges,byTag,"d","b",2);
        createEdge(edges,byTag,"d","e",4);

        createEdge(edges,byTag,"e","b",6);
        createEdge(edges,byTag,"e","d",4);


        Dijkstra dijkstra = new Dijkstra(nodes,edges,byTag.get("a"));
        dijkstra.doDijkstra();

        Map<Integer,NodeInfo> infoMap = dijkstra.getInfo();

        for (NodeInfo info :
                infoMap.values()) {
            Node from = byId.get(info.getNodeId());
            Node prev = byId.get(info.getPrevNode());

            System.out.println( from.getTag() +","+info.getDistance() +","+( prev == null ? "": prev.getTag()));
        }


    }

    private static void createEdge( List<Edge> edges,Map<String, Node> byTag , String a, String b, long distance){
        edges.add(new Edge(byTag.get(a).getId(),byTag.get(b).getId(),distance));
    }


}
