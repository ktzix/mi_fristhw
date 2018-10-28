package hu.bme.jschneid.maze;

public class MazeRuleTest {


    public static void main(String[] args) {

//        print(1);
//        print(2);
//        print(4);
//        print(8);
//        print(16);
//        print(3);
//        print(7);
//        print(15);
//        print(31);

        print(7);


    }


    public static void print(int num){
        MazeRule rule = new MazeRule(0,0,num);
        System.out.println("rule: "+ num);
        System.out.println("north: "+ rule.isBorderNorth());
        System.out.println("south: "+ rule.isBorderSouth());
        System.out.println("west: "+ rule.isBorderWest());
        System.out.println("east: "+ rule.isBorderEast());
        System.out.println("item: "+ rule.isItem());


    }

}
