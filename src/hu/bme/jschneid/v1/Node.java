package hu.bme.jschneid.v1;

import java.util.Objects;

public class Node {

    boolean borderWest;
    boolean borderEast;
    boolean borderNorth;
    boolean borderSouth;
    boolean artifact;
    boolean visited;
    boolean wrong;

    private int x;
    private int y;


    public Node(int x, int y, int item) {
        if (item >= 16) {
            item -= 16;
            this.artifact = true;
        }
        if (item >= 8) {
            item -= 8;
            this.borderWest = true;
        }
        if (item >= 4) {
            item -= 4;
            this.borderSouth = true;
        }
        if (item >= 2) {
            item -= 2;
            this.borderEast = true;
        }
        if (item >= 1) {
            this.borderNorth = true;
        }
        this.x=x;
        this.y=y;
    }

    public boolean isBorderEast() {
        return borderEast;
    }

    public boolean isArtifact() {
        return artifact;
    }

    public boolean isBorderNorth() {
        return borderNorth;
    }

    public boolean isBorderSouth() {
        return borderSouth;
    }

    public boolean isBorderWest() {
        return borderWest;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isWrong() {
        return wrong;
    }

    public void setWrong(boolean wrong) {
        this.wrong = wrong;
    }

    public void setArtifact(boolean artifact) {
        this.artifact = artifact;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
