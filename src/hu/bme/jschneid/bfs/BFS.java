package hu.bme.jschneid.bfs;

import hu.bme.jschneid.common.Graph;
import hu.bme.jschneid.common.Node;
import hu.bme.jschneid.maze.Maze;
import hu.bme.jschneid.maze.MazeInfo;
import hu.bme.jschneid.maze.MazeRule;
import hu.bme.jschneid.v1.Main;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BFS<T> {

    private Map<Node, Color> color = new HashMap<>();
    private Map<Node, Integer> distance = new HashMap<>();
    private Map<Node, Node> parent = new HashMap<>();

    public void search(Graph g, Node start) {
        Set<Node<T>> nodes = new HashSet<>(g.getNodes());
        nodes.remove(start);

        for (Node node : nodes) {
            color.put(node, Color.WHITE);
            distance.put(node, Integer.MAX_VALUE);
            parent.put(node,null);
        }

        color.put(start, Color.GRAY);
        distance.put(start, 0);
        parent.put(start, null);

        Queue<Node<T>> queue = new LinkedList();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node u = queue.remove();
            List<Node<T>> children = g.getChildren(u);
            for (Node child :
                    children) {
                if (color.get(child) == Color.WHITE) {
                    color.put(child, Color.GRAY);
                    distance.put(child, distance.get(u) + 1);
                    parent.put(child, u);
                    queue.add(child);
                }
                color.put(child, Color.BLACK);
            }

        }
    }

    public List<Node<T>> getPath(Node target){
        List<Node<T>> result = new ArrayList<>();

        Node<T> node = target;
        while ( node != null ){
            result.add(node);
            node = this.parent.get(node);
        }

        Collections.reverse(result);
        return result;
    }


    public static void main(String[] args) {
        Graph<MazeRule, MazeInfo> graph = Maze.parse(Main.INPUT);
        BFS bfs = new BFS();
        bfs.search(graph,graph.getNodeByTag("start"));

        List<Node> path = bfs.getPath(graph.getNodeById(5));

        System.out.println("ok");
    }

    public Map<Node, Integer> getDistance() {
        return distance;
    }
}
