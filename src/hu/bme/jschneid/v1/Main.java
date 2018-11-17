package hu.bme.jschneid.v1;

import hu.bme.jschneid.common.Edge;
import hu.bme.jschneid.common.Graph;
import hu.bme.jschneid.maze.Maze;
import hu.bme.jschneid.maze.MazeInfo;
import hu.bme.jschneid.maze.MazeRouteFinder;
import hu.bme.jschneid.maze.MazeRule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public int[] items = null;

    static String[] inputItems = null;
    static BufferedReader br = null;
    static List<Integer> listOfStrings = new ArrayList<Integer>();
    static int row = 0;
    static int col = 0;
    static int ArtifactCounter = 0;
   static List<Edge> edgeList =new ArrayList<Edge>();

   public static String INPUT = "10 9 7\n" +
           "8 0 19\n" +
           "12 4 2\n" +
           "1\n";


    /**
     * ‎10 9 7
     * 8 0 19
     * 12 4 2
     * 1
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

     readStream(System.in);
// readStream(new ByteArrayInputStream(INPUT.getBytes(StandardCharsets.UTF_8)));




    }


    public static void readStream(InputStream stream) throws Exception {

        try {
            String inputString;
            boolean firstLine = true;
            StringBuffer sb = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(stream));
            while (!(inputString = br.readLine()).equals("")) {

                sb.append(inputString);
                sb.append("\n");
            }

            br.close();


            Graph<MazeRule, MazeInfo> graph = Maze.parse(sb.toString());
            MazeRouteFinder mazeRouteFinder = new MazeRouteFinder();
            mazeRouteFinder.findRoute(graph);
            mazeRouteFinder.printActions();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException ne) {

        }







    }



}
