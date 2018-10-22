import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public int[] items = null;
    static String inputString;
    static String[] inputItems = null;
    static BufferedReader br = null;
    static List<Integer> listOfStrings = new ArrayList<Integer>();
    static int row = 0;
    static int col = 0;
    static int ArtifactCounter = 0;
   static List<Edge> edgeList =new ArrayList<Edge>();

    public static void main(String[] args) throws Exception {

       readStream(System.in);
//       readStream(( R);



    }


    public static void readStream(InputStream stream) throws Exception {

        try {
            br = new BufferedReader(new InputStreamReader(stream));
            while ((inputString = br.readLine()) != null) {

                inputItems = inputString.split(" ");


                for (int i = 0; i < inputItems.length; i++) {

                    listOfStrings.add(Integer.parseInt(inputItems[i]));
                }

                row++;

            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException ne) {

        }

        row--;
        col = (listOfStrings.size() - 1) / row;

        ArtifactCounter = listOfStrings.get(listOfStrings.size() - 1);

        listOfStrings.remove(listOfStrings.size() - 1);

        Map map = new Map(col, row);
        map.addElements(listOfStrings);

        RouteFinder finder = new RouteFinder(map.getMap(),row,col, ArtifactCounter);

        finder.search();
        finder.printRoute();
        //  map.findNeighbors(map, edgeList, row, col  );

        //System.out.println(edgeList.size());




    }

   /** List<Node> queue = new ArrayList<>();

    void BFS( Node node) {


        if (!node.isVisited()) {
            node.setVisited(true);
            queue.add(node);
        }

            for (int iter = 0; iter < edgeList.size(); iter++) {
                while (edgeList.get(iter) != null) {
                    if (edgeList.get((iter+1)) != null ) {
                        if(edgeList.get(iter).getB().isArtifact()){
                            queue.add(edgeList.get(iter).getB());
                            edgeList.get(iter).getB().setVisited(true);
                            BFS(edgeList.get(iter).getA());
                        }
                        edgeList.remove(iter+1);
                        BFS(edgeList.get(iter).getA());
                    }
                    Node e = edgeList.get(iter+1).getA();
                    if (!node.visited) {
                        e.visited = true;
                        queue.add(e);
                    }
                }
            }

    }*/


}
