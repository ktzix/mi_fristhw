package hu.bme.jschneid.maze;

import hu.bme.jschneid.common.Edge;
import hu.bme.jschneid.common.Graph;
import hu.bme.jschneid.common.Node;
import hu.bme.jschneid.v1.Main;

import java.util.Arrays;
import java.util.Set;

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

    public static void main(String[] args) {

        Graph<MazeRule, MazeInfo> graph = Maze.parse(Main.INPUT);

        System.out.println("Item count: " + graph.getPayload().getItemCount());

        Set<Node<MazeRule>> nodes = graph.getNodes();

        for (Node<MazeRule> node : nodes) {
            System.out.println("node: " + node.getId() + " " + (node.getPayload().isItem() ? "Item" : ""));
        }

        System.out.println("edges");
        for (Edge<MazeRule> edge : graph.getEdges()) {
            System.out.println("node: " + edge.getNode().getId() + " - " + edge.getOppositeNode().getId());
        }
    }

}
