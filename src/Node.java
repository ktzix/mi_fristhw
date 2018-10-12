public class Node {

    boolean west;
    boolean east;
    boolean north;
    boolean south;
    boolean artifact;


    public Node(int item) {
        if (item >= 16) {
            item -= 16;
            this.artifact = true;
        }
        if (item >= 8) {
            item -= 8;
            this.west = true;
        }
        if (item >= 4) {
            item -= 4;
            this.south = true;
        }
        if (item >= 2) {
            item -= 2;
            this.east = true;
        }
        if (item >= 1) {
            this.north = true;
        }
    }

    public boolean isEast() {
        return east;
    }

    public boolean isArtifact() {
        return artifact;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

}
