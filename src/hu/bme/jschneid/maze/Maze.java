package hu.bme.jschneid.maze;

import hu.bme.jschneid.bfs.BFS;
import hu.bme.jschneid.common.Edge;
import hu.bme.jschneid.common.Graph;
import hu.bme.jschneid.common.Node;
import hu.bme.jschneid.dijkstra.Dijkstra;
import hu.bme.jschneid.dijkstra.DijkstraNodeDistance;
import hu.bme.jschneid.v1.Main;

import java.util.*;
import java.util.stream.Collectors;

public class Maze {


    @SuppressWarnings("WeakerAccess")
    public static Graph<MazeRule, MazeInfo> parse(String mazeDefinition) {
        // split string to rows
        String rows[] = mazeDefinition.split("\\r?\\n");
        // last row ins count of items
        String countLine = rows[rows.length - 1];
        Integer itemCount = Integer.parseInt(countLine);
        // drop the last "itemCount" row
        rows = Arrays.copyOf(rows, rows.length - 1);
        // current node id
        int nodeId = 0;
        // count of rows
        int rowCount = rows.length;
        // count of columns
        int colCount = rows[0].split(" ").length;
        // init the empty graph
        Graph<MazeRule, MazeInfo> graph = new Graph<>(new MazeInfo(itemCount));
        // create the matrix of nodes
        //noinspection unchecked
        Node<MazeRule>[][] matrix = new Node[rowCount][colCount];
        //add nodes to graph
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            String[] cells = rows[rowIndex].split(" ");
            for (int colIndex = 0; colIndex < colCount; colIndex++) {
                String tag = "";
                if (rowIndex == 0 && colIndex == 0) {
                    tag = "start";
                } else if (rowIndex == (rowCount - 1) && colIndex == (colCount - 1)) {
                    tag = "exit";
                }
                MazeRule rule = new MazeRule(rowIndex, colIndex, Integer.parseInt(cells[colIndex]));
                Node<MazeRule> node = new Node<>(nodeId, tag, rule);
                graph.addNode(node);
                matrix[rowIndex][colIndex] = node;
                nodeId++;
            }
        }

        // add edges to graph
        for (Node<MazeRule> node : graph.getNodes()) {

            MazeRule rule = node.getPayload();
            if (!rule.isBorderEast()) {
                try {
                    Node<MazeRule> east = matrix[rule.getRow()][rule.getCol() + 1];
                    graph.addEdge(node, east, 1L);
                } catch (Exception e) {
                    // cell not exists
                }
            }

            if (!rule.isBorderWest()) {
                try {
                    Node<MazeRule> west = matrix[rule.getRow()][rule.getCol() - 1];
                    graph.addEdge(node, west, 1L);
                } catch (Exception e) {
                    // cell not exists
                }
            }

            if (!rule.isBorderNorth()) {
                try {
                    Node<MazeRule> north = matrix[rule.getRow() - 1][rule.getCol()];
                    graph.addEdge(node, north, 1L);
                } catch (Exception e) {
                    // cell not exists
                }
            }

            if (!rule.isBorderSouth()) {
                try {
                    Node<MazeRule> south = matrix[rule.getRow() + 1][rule.getCol()];
                    graph.addEdge(node, south, 1L);
                } catch (Exception e) {
                    // cell not exists
                }
            }

        }

        return graph;

    }

    public static void main(String[] args) throws Exception {

        Graph<MazeRule, MazeInfo> graph = Maze.parse(Main.INPUT);

        System.out.println("Item count: " + graph.getPayload().getItemCount());

        Set<Node<MazeRule>> nodes = graph.getNodes();

//        for (Node<MazeRule> node : nodes) {
//            System.out.println("node: " + node.getId() + " " + (node.getPayload().isItem() ? "Item" : ""));
//        }

//        System.out.println("edges");
//        for (Edge<MazeRule> edge : graph.getEdges()) {
//            System.out.println("node: " + edge.getNode().getId() + " - " + edge.getOppositeNode().getId());
//        }

        List<Node<MazeRule>> steps = new ArrayList<>();

        Node<MazeRule> startNode = graph.getNodeByTag("start");
        steps.add(startNode);
        Node<MazeRule> position = startNode;

        List<Node<MazeRule>> items = graph.getNodes().stream().filter(node -> node.getPayload().isItem()).collect(Collectors.toList());
        items.remove(startNode);

        Map<Node<MazeRule>, DijkstraNodeDistance> dijkstra;
        while (!items.isEmpty()) {

            BFS bfs = new BFS();
            bfs.search(graph, position);

            Node<MazeRule> nextPosition = findItemWithShortestDistance(bfs.getDistance(), items);
            List<Node<MazeRule>> routeFromPositionToNextPosition = bfs.getPath(nextPosition);
        routeFromPositionToNextPosition.remove(0);
            steps.addAll(  routeFromPositionToNextPosition);
            items.remove(nextPosition);
            position = nextPosition;

        }

        Node<MazeRule> exitNode = graph.getNodeByTag("exit");

        BFS bfs = new BFS();
        bfs.search(graph, position);
        List<Node<MazeRule>> routeFromPositionToNextPosition = bfs.getPath(exitNode);
        routeFromPositionToNextPosition.remove(0);
        steps.addAll(  routeFromPositionToNextPosition);

        for (Node<MazeRule> step:    steps        ) {
            System.out.println(step.getId());
        }

    }


    public static Node<MazeRule> findItemWithShortestDistance(Map<Node, Integer> dist, List<Node<MazeRule>> items) {
        Node<MazeRule> candidate = null;
        long distance = Integer.MAX_VALUE;
        for (Node<MazeRule> item : items) {
            Integer currentDistance = dist.get(item);
            if (currentDistance < distance) {
                distance = currentDistance;
                candidate = item;
            }

        }
        return candidate;
    }

    /**
     * Get the steps of the route from the source position to the target position with the help of dijkstra.
     * The step to source node is not included.
     * The step to target node is included.
     *
     * @param source      the source node
     * @param target      the target node
     * @param dijkstraMap the dijkstra distances
     * @return the route from source to destination
     * @throws Exception if there is no route from source to target
     */
    public static List<Node<MazeRule>> getShortestRoute(Node<MazeRule> source, Node<MazeRule> target, Map<Node<MazeRule>, DijkstraNodeDistance> dijkstraMap) throws Exception {
        List<Node<MazeRule>> route = new ArrayList<>();

        Node<MazeRule> position = target;
        DijkstraNodeDistance distance;
        while (position != source) {
            if (position == null) {
                throw new Exception("Route not found from " + source.getId() + " to " + target.getId());
            }
            distance = dijkstraMap.get(target);
            if (distance == null) {
                throw new Exception("DijkstraMap - Route  not found from " + source.getId() + " to " + target.getId());
            }
            route.add(position);
            position = distance.getPrevNode();
        }


        Collections.reverse(route);

        return route;
    }

}
