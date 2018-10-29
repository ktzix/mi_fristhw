package hu.bme.jschneid.maze;

import hu.bme.jschneid.bfs.BFS;
import hu.bme.jschneid.common.Graph;
import hu.bme.jschneid.common.Node;
import hu.bme.jschneid.v1.Main;

import java.util.*;
import java.util.stream.Collectors;

public class Maze {


    public static final String START = "start";
    public static final String EXIT = "exit";

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
                    tag = START;
                } else if (rowIndex == (rowCount - 1) && colIndex == (colCount - 1)) {
                    tag = EXIT;
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

        List<Node<MazeRule>> steps = new ArrayList<>();

        Node<MazeRule> startNode = graph.getNodeByTag("start");
        steps.add(startNode);
        Node<MazeRule> position = startNode;

        List<Node<MazeRule>> items = graph.getNodes().stream().filter(node -> node.getPayload().isItem()).collect(Collectors.toList());
        items.remove(startNode);

        while (!items.isEmpty()) {

            BFS bfs = new BFS();
            bfs.search(graph, position);

            //noinspection unchecked
            Node<MazeRule> nextPosition = findItemWithShortestDistance(bfs.getDistance(), items);

            if ( nextPosition == null ){
                throw new Exception("route not found from position " + position.getId());
            }

            //noinspection unchecked
            List<Node<MazeRule>> routeFromPositionToNextPosition = bfs.getPath(nextPosition);
        routeFromPositionToNextPosition.remove(0);
            steps.addAll(  routeFromPositionToNextPosition);
            items.remove(nextPosition);
            position = nextPosition;

        }

        Node<MazeRule> exitNode = graph.getNodeByTag("exit");

        BFS bfs = new BFS();
        bfs.search(graph, position);
        //noinspection unchecked
        List<Node<MazeRule>> routeFromPositionToNextPosition = bfs.getPath(exitNode);
        routeFromPositionToNextPosition.remove(0);
        steps.addAll(  routeFromPositionToNextPosition);

        for (Node<MazeRule> step:    steps        ) {
            System.out.println(step.getId());
        }

    }


    private static Node<MazeRule> findItemWithShortestDistance(Map<Node, Integer> dist, List<Node<MazeRule>> items) {
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



}
