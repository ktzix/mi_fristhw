package hu.bme.jschneid.maze;

import hu.bme.jschneid.bfs.BFS;
import hu.bme.jschneid.common.Graph;
import hu.bme.jschneid.common.Node;
import hu.bme.jschneid.v1.Main;

import java.util.*;
import java.util.stream.Collectors;

public class MazeRouteFinder {


    private Set<Node> items = new HashSet<>();
    private List<Action> actions = new ArrayList<>();

    public void findRoute(Graph mazeGraph) throws Exception {

        //noinspection unchecked
        Node<MazeRule> startNode = mazeGraph.getNodeByTag(Maze.START);
        actions.add(new Action(startNode, ActionType.Step));

        if (startNode.getPayload().isItem() && items.contains(startNode)) {
            actions.add(new Action(startNode, ActionType.Collect));
        }

        Node<MazeRule> position = startNode;

        //noinspection unchecked
        List<Node<MazeRule>> items = (List<Node<MazeRule>>) mazeGraph.getNodes().stream().filter(node -> ((Node<MazeRule>) node).getPayload().isItem()).collect(Collectors.toList());
        items.remove(startNode);

        while (!items.isEmpty()) {

            BFS bfs = new BFS();
            bfs.search(mazeGraph, position);

            //noinspection unchecked
            Node<MazeRule> nextPosition = findClosestItem(bfs.getDistance(), items);

            if (nextPosition == null) {
                throw new Exception("route not found from position " + position.getId());
            }

            //noinspection unchecked
            List<Node<MazeRule>> routeFromPositionToNextPosition = bfs.getPath(nextPosition);
            // remove the first step from the route, which is already in the actions
            routeFromPositionToNextPosition.remove(0);
            addActions(routeFromPositionToNextPosition);
            items.remove(nextPosition);
            position = nextPosition;

        }

        //noinspection unchecked
        Node<MazeRule> exitNode = mazeGraph.getNodeByTag(Maze.EXIT);

        BFS bfs = new BFS();
        bfs.search(mazeGraph, position);
        //noinspection unchecked
        List<Node<MazeRule>> routeFromPositionToNextPosition = bfs.getPath(exitNode);
        routeFromPositionToNextPosition.remove(0);
        addActions(routeFromPositionToNextPosition);
    }

    private void addActions(List<Node<MazeRule>> nodes) {
        for (Node<MazeRule> node :
                nodes) {
            addAction(node);
        }
    }

    private void addAction(Node<MazeRule> node) {
        actions.add(new Action(node, ActionType.Step));

        if (node.getPayload().isItem() && !items.contains(node)) {
            actions.add(new Action(node, ActionType.Collect));
            items.add(node);
        }
    }

    private Node<MazeRule> findClosestItem(Map<Node, Integer> dist, List<Node<MazeRule>> items) {
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


    @SuppressWarnings("WeakerAccess")
    public void printActions(){
        for (Action action:    actions        ) {
            System.out.println(action.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        Graph<MazeRule, MazeInfo> graph = Maze.parse(Main.INPUT);
        MazeRouteFinder mazeRouteFinder = new MazeRouteFinder();
        mazeRouteFinder.findRoute(graph);
        mazeRouteFinder.printActions();
    }

}
