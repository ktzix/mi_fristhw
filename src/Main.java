import java.io.BufferedReader;
import java.io.IOException;
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

    public static void main(String[] args) {

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
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


        map.findNeighbors(map, edgeList, row, col  );

        System.out.println(edgeList.size());



    }


}
