import java.util.List;

public class Map {

    Node[][] map;
  public static int row;
  public static int col;
    int counter = 0;

    public Map(int col, int row) {

        this.row = row;
        this.col = col;
        map = new Node[row][col];

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public void addElements(List<Integer> elements) {

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                if(elements.size() !=0)
                map[i][j] = new Node(elements.get(0));
                elements.remove(0);

            }

    }

    public Node getNode(int row, int col){
        Node temp= null;
        temp=map[row][col];
        return temp;

    }

    void findNeighbors(Map map, List<Edge> list, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!map.getNode(i, j).isSouth() && i!=row-1) {
                    list.add(new Edge(map.getNode(i, j), map.getNode(i+1, j)));
                }
               // if (!map.getNode(i, j).isWest()&& i!=0) {
                //    list.add(new Edge(map.getNode(i, j), map.getNode(i - 1, j)));
                //}
             //   if (!map.getNode(i, j).isNorth() && j!=0) {
               //     list.add(new Edge(map.getNode(i, j), map.getNode(i, j - 1)));
                //}
                if (!map.getNode(i, j).isEast() && j!=col-1) {
                    list.add(new Edge(map.getNode(i, j), map.getNode(i , j+1)));
                }
            }
        }

    }


}
