import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Display {

    private CityMap cityMap = new CityMap();
    private String source;
    private String destination;
    private String method;
    private Scanner scanner = new Scanner(System.in);

    public Display(){
        display();
    }
    public void display(){
        System.out.println("Given a list of city below, please type the name of source and destination: ");
        Integer count = 1;
        for (String city : cityMap.cityCoordinate.keySet()){
            System.out.print(count + "." + city.toString() + "  ");
            if (count%10==0)
                System.out.print("\n");
            count++;
        }
        System.out.println("\nEnter starting town: ");
        source = scanner.nextLine();
        System.out.println("Enter ending town: ");
        destination = scanner.nextLine();
        System.out.println("Choose a method below by input its index: \n" +
                "1. Depth First Search\n" +
                "2. Breadth First Search\n" +
                "3. DFS with Iterative Deepening\n" +
                "4. Best First Search\n" +
                "5. A* Search");
        method = scanner.nextLine();
        displayResult();
        while (!method.equals("0")) {
            System.out.println("Choose other methods for comparison? OR type 0 to exist");
            method = scanner.nextLine();
            displayResult();
        }
    }
    private List <String[]> routeCoordination (List<String> route){
        List<String[]> coordinationLists = new ArrayList<>();
        for(String city :route){
            coordinationLists.add(cityMap.cityCoordinate.get(city));
        }
        return coordinationLists;
    }

    private List <String> route (){
        if (method.equals("1")) {
            DepthFirstSearch DFS = new DepthFirstSearch(cityMap);
            return DFS.findRoute(source,destination);
        }
        else if (method.equals("2")){
            BreadthFirstSearch BFS = new BreadthFirstSearch(cityMap);
            return BFS.findRoute(source,destination);
        }
        else if (method.equals("3")){
            DepthFirstSearchWithIterativeDeepening IdDFS = new DepthFirstSearchWithIterativeDeepening(cityMap);
            return IdDFS.findRoute(source, destination);
        }
        else if (method.equals("4")) {
            BestFirstSearch bestSearch = new BestFirstSearch(cityMap);
            return bestSearch.findRoute(source,destination);
        }
        else if(method.equals("5")){
            AStartSearch aStartSearch = new AStartSearch(cityMap);
            return aStartSearch.findRoute(source, destination);
        }else
            return null;
    }
    public void displayResult (){
        List<String> route= route ();
        if (route != null) {
            System.out.println("Route from " + source + " to " + destination + ":");
            for (String node : route) {
                if (node.equals(route.getLast()))
                    System.out.print(node + " ");
                else System.out.print(node + " -> ");
            }
            Distance distance =new Distance(cityMap);
            System.out.println("\nWith " + distance.calculateTotalDistance(route)+ " Kilometers");
            new GoogleMapHTMLGenerator(routeCoordination(route));

        } else {
            System.out.println("No route found from " + source + " to " + destination);
        }
    }


}
