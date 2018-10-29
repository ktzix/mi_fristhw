package hu.bme.jschneid.dijkstra;


import hu.bme.jschneid.common.Edge;
import hu.bme.jschneid.common.Node;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra<T> {


    private static final Long INFINITY = Long.MAX_VALUE;

    private Node<T> start;
    private Collection<Node<T>> nodes;
    private Collection<Edge> edges;
    private Map<Node<T>, DijkstraNodeDistance> info;
    private Map<Node<T>, List<Edge>> adjacencyListMap;
    private Set<Node<T>> unvisited;

    public static <T> Map<Node<T>, DijkstraNodeDistance> dijkstra(Collection<Node<T>> nodes, Collection<Edge<T>> edges, Node<T> start){
        Dijkstra<T> dijkstra = new Dijkstra(nodes,edges,start);
        dijkstra.doDijkstra();
        return dijkstra.getInfo();
    }

    @SuppressWarnings("WeakerAccess")
    public Dijkstra(Collection<Node<T>> nodes, Collection<Edge> edges, Node<T> start) {
        this.start = start;
        this.nodes = nodes;
        this.edges = edges;


        unvisited = new HashSet<>(nodes);

        prepareNodeInfo();
        prepareAdjacencyLists();
    }

    private void prepareNodeInfo() {
        info = new HashMap<>();
        for (Node<T> node :
                nodes) {
            Long distance = start.getId().equals(node.getId()) ? 0 : INFINITY;
            info.put(node, new DijkstraNodeDistance(node, distance));
        }
    }

    /**
     * create adjacency lists ( szomszédsági lista)
     */
    private void prepareAdjacencyLists() {
        adjacencyListMap = new HashMap<>();
        for (Edge edge : edges) {
            adjacencyListMap.putIfAbsent(edge.getNode(), new ArrayList<>());
            adjacencyListMap.get(edge.getNode()).add(edge);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void doDijkstra() {
        doDijkstra(start);
    }

    private void doDijkstra(Node<T> node) {

        System.out.println("Dijkstra: " + node.getId());
        // remove from unvisited
        unvisited.remove(node);

        // get the list of neighbour edges
        List<Edge> neighbours = this.adjacencyListMap.getOrDefault(node, Collections.emptyList());

        // get the distance of the actual node from the staring node
        long currentDistance = info.get(node).getDistance();

        // calculate distance to neighbours
        for (Edge edge : neighbours) {
            DijkstraNodeDistance oppositeDijkstraNodeDistance = info.get(edge.getOppositeNode());
            long savedOppositeNodeDistance = oppositeDijkstraNodeDistance.getDistance();
            long actualOppositeNodeDistance = currentDistance + edge.getDistance();
            // if we have found a shorter node, update the info
            if (actualOppositeNodeDistance < savedOppositeNodeDistance) {
                oppositeDijkstraNodeDistance.setDistance(actualOppositeNodeDistance);
                oppositeDijkstraNodeDistance.setPrevNode(node);
            }
        }


        // find neighbour with shortest way from the start point
        List<Node> listOfOppositeNodeIds = neighbours.stream().map(Edge::getOppositeNode).collect(Collectors.toList() );
        List<DijkstraNodeDistance> neighbourDijkstraNodeDistances = info.values().stream()
                .filter(dijkstraNodeDistance -> listOfOppositeNodeIds.contains(dijkstraNodeDistance.getNode()))
                .filter(dijkstraNodeDistance -> unvisited.contains(dijkstraNodeDistance.getNode()))
                .sorted((o1, o2) -> (int) (o1.getDistance() - o2.getDistance()))
                .collect(Collectors.toList());


        Node<T> oppositeNodeWithShortestDistanceFromStartPoint = null;
        // select shortest distance
        if (!neighbourDijkstraNodeDistances.isEmpty()) {
            DijkstraNodeDistance info = neighbourDijkstraNodeDistances.get(0);
            oppositeNodeWithShortestDistanceFromStartPoint = info.getNode();
        }

        if (oppositeNodeWithShortestDistanceFromStartPoint != null) {
            doDijkstra(oppositeNodeWithShortestDistanceFromStartPoint);
        }

    }

    @SuppressWarnings("WeakerAccess")
    public Map<Node<T>, DijkstraNodeDistance> getInfo() {
        return info;
    }

    public static void main(String[] args) {
        String[] nodeNames = {"a", "b", "c", "d", "e"};
        List<Node<Object>> nodes = new ArrayList<>();
        Map<String, Node> byTag = new HashMap<>();
        int i = 0;
        for (String nodeName : nodeNames) {
            Node node = new Node(i, nodeName);
            nodes.add(node);
            i++;
            byTag.put(nodeName,node);
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

        Map<Node, DijkstraNodeDistance> infoMap = dijkstra.getInfo();

        for (DijkstraNodeDistance info :
                infoMap.values()) {
            Node from =info.getNode();
            Node prev = info.getPrevNode();

            System.out.println( from.getTag() +","+info.getDistance() +","+( prev == null ? "": prev.getTag()));
        }


    }

    private static void createEdge(List<Edge> edges, Map<String, Node> byTag , String a, String b, long distance){
        edges.add(new Edge(byTag.get(a),byTag.get(b),distance));
    }


}
