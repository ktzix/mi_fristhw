package hu.bme.jschneid.v1;

import hu.bme.jschneid.v1.Node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RouteFinder {

    public static int MAX_VISITED = 3;

    private Node[][] matrix;

    private int artifactCount = 0;
    private int totalArtifactCount = 0;

    private List<Node> route = new ArrayList<>();

    private int rowCount;

    private int columnCount;

    private java.util.Map<Node, AtomicInteger> visitedCounter = new HashMap<>();
    private java.util.Map<Node,java.util.Map<Direction, AtomicInteger>> visitedDirectionCounter = new HashMap<>();
    private java.util.Map<Node, Set<Node>> visitedNodes = new HashMap<>();



    public RouteFinder(Node[][] matrix, int rowCount, int columnCount, int totalArtifactCount) throws Exception {
        this.matrix = matrix;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.totalArtifactCount = totalArtifactCount;
    }

    public void search() throws Exception {
        Node startNode = matrix[0][0];
        searchRoute(null,startNode, Direction.NORD);
    }

    public void printRoute() {
        Set<Node> visitedNodes = new HashSet<>();

        for (Node step : route) {
            boolean visited = visitedNodes.contains(step);
            boolean hasArtifact = step.isArtifact();
            System.out.println(step.getX() + "," + step.getY() + ((!visited && hasArtifact) ? ", artifact" : ""));
            visitedNodes.add(step);
        }
    }

    public boolean searchRoute(
            Node from,
           final  Node current,
            Direction fromDirection
    ) throws Exception {

        addNodeVisitedDirection(from,fromDirection);
        if( from != null ){
            addNodeVisitedFromNode(from,current);;
        }

        if ( getVisitedCount(current) >= MAX_VISITED ){
            return  false;
        }

        incNodeVisitedCount(current);

        route.add(current);

        if (current.isArtifact()) {
            this.artifactCount = this.artifactCount + 1;
        }

        boolean allArtifactsFound = artifactCount == totalArtifactCount;
        if (allArtifactsFound && this.isExitNode(current)) {
            return true;
        }


        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
        directions.sort((o1, o2) -> {
            int o1VisitedCount = getDirectionVisisted(current,o1);
            int o2VisitedCount = getDirectionVisisted(current,o2);
            return o1VisitedCount - o2VisitedCount;
        });


        for (Direction direction : directions) {
            // if we are in start position
            if (this.isFirstNode(current) && direction == Direction.NORD) {
                // skip this direction
                continue;
            }

            if(direction == fromDirection){
                continue;
            }
            if (canGoInDirection(current, direction)) {
                Node next = getNextNode(current, direction);

//                if ( isNodeVisitedFromNode(current,next)){
//                    continue;
//                }

                boolean exitReachedWithAllArtifact = searchRoute(current,next, direction);
                if (exitReachedWithAllArtifact) {
                    return true;
                }else{
                    if ( !route.get(route.size() -1 ).equals(current )){
                        route.add(current);
                    }
                }

            }
        }
        return false;
    }



    private Node getNextNode(Node current, Direction direction) throws Exception {
        Node result = null;
        for (int i = 0; i < matrix.length; i++) {
            Node[] row = matrix[i];
            for (int j = 0; j < row.length; j++) {
                Node node = matrix[i][j];
                if (node.getY() == current.getY() && node.getX() == current.getX()) {

                    switch (direction) {
                        case NORD:
                            result = matrix[i - 1][j];
                            break;
                        case WEST:
                            result = matrix[i][j - 1];
                            break;
                        case SOUTH:
                            result = matrix[i + 1][j];
                            break;
                        case EAST:
                            result = matrix[i][j + 1];
                            break;
                        default:
                            throw new Exception("can't go to next node  from node(" + current.getX() + ", " + current.getY() + ") in direction : " + direction);
                    }
                    return result;
                }
            }
        }
        throw new Exception("can't find next node from node(" + current.getX() + ", " + current.getY() + ")" + direction);
    }


    private boolean canGoInDirection(Node node, Direction direction) throws Exception {
        boolean result;
        switch (direction) {

            case NORD:
                result = !node.isBorderNorth() && ( (node.getY() -1) > -1 );
                break;
            case WEST:
                result = !node.isBorderWest() &&  ((node.getX()-1 ) > -1 ) ;
                break;
            case SOUTH:
                result = !node.isBorderSouth() && ( (node.getY() +1) < this.rowCount );
                break;
            case EAST:
                result = !node.isBorderEast() &&  ((node.getX()+1 ) < this.columnCount ) ;
                break;
            default:
                throw new Exception("Invalid direction" + direction);
        }
        return result;
    }

    public boolean isExitNode(Node node) {
        return node.getX() == (columnCount - 1) && node.getY() == (rowCount - 1);
    }

    public boolean isFirstNode(Node node) {
        return node.getX() == 0 && node.getY() == 0;
    }

    private void incNodeVisitedCount(Node node ){
        if ( !this.visitedCounter.containsKey(node)){
            this.visitedCounter.put(node, new AtomicInteger(0));
        }
        visitedCounter.get(node).incrementAndGet();
    }

    private Integer getVisitedCount(Node node ){
        if ( this.visitedCounter.containsKey(node)){
            return visitedCounter.get(node).get();
        }
        return 0;
    }
    private void addNodeVisitedFromNode(Node current,Node node ){
        if ( !this.visitedNodes.containsKey(current)){
            this.visitedNodes.put(current, new HashSet<>());
        }
        visitedNodes.get(current).add(node);
    }

    private boolean isNodeVisitedFromNode(Node current,Node node){
        if ( this.visitedNodes.containsKey(current)){
            return visitedNodes.get(current).contains(node);
        }
        return false;
    }
   private void addNodeVisitedDirection(Node current,Direction direction ){
       visitedDirectionCounter.putIfAbsent(current, new HashMap<>());
       visitedDirectionCounter.get(current).putIfAbsent(direction, new AtomicInteger(0));
       visitedDirectionCounter.get(current).get(direction).incrementAndGet();
    }

    private int getDirectionVisisted(Node current,Direction direction ){
        if ( this.visitedDirectionCounter.containsKey(current)){
            if ( visitedDirectionCounter.get(current).containsKey(direction)){
                return visitedDirectionCounter.get(current).get(direction).get();
            }
        }
        return 0;
    }

    public static enum Direction {
        SOUTH,
        EAST,
        WEST,
        NORD
    }

}
