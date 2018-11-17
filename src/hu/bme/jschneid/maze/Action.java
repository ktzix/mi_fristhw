package hu.bme.jschneid.maze;

import hu.bme.jschneid.common.Node;

public class Action  {

    public static final String COLLECT = "felvesz";
    private Node<MazeRule>  node;
    private ActionType action;

    public Action(Node  node, ActionType action) {
        this.node = node;
        this.action = action;
    }

    public Node getNode() {
        return node;
    }

    public ActionType getAction() {
        return action;
    }

    @Override
    public String toString() {
        if ( action == ActionType.Collect){
            return COLLECT;
        }
        return node.getPayload().getRow() + " " + node.getPayload().getCol();
    }
}
