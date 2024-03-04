import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class CityMap {

    private final String adjacenciesFile = "G:\\Shared drives\\yntp\\CS461- Introduction to AI\\Assignment\\Search methods\\src\\adjacencies.txt";
    private final String coordinatesFile = "G:\\Shared drives\\yntp\\CS461- Introduction to AI\\Assignment\\Search methods\\src\\coordinates.csv";

    public ArrayList <String[]> cityAdjacency= new ArrayList<>();
    public Map<String, String[]> cityCoordinate = new HashMap<>();
    public Map <String, ArrayList<String>> mapAdjacency = new HashMap<>();
    public Map<String[], Double> weightMap = new HashMap<>();

    public CityMap(){
        setCityAdjacency();
        setCityCoordinate();
        setMapAdjacency();
    }

    private void setCityAdjacency (){
        try (BufferedReader reader = new BufferedReader(new FileReader(adjacenciesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cityPair = line.split(" ");
                cityAdjacency.add(cityPair);
            }
        } catch (IOException e) {
            System.out.println("Cannot find the file");
        }
//       for (String[] key :cityAdjacency) {
//            System.out.println(key[0]+ "  "+key [1]);
//        }
    }

    public void setCityCoordinate(){
        try (BufferedReader reader = new BufferedReader(new FileReader(coordinatesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String[] coordinate = new String[2];
                coordinate[0]=data[1];
                coordinate[1]=data[2];
                cityCoordinate.put(data[0], coordinate);
            }
        } catch (IOException e) {
            System.out.println("Cannot find the file");
        }
//        for (String key :cityCoordinate.keySet()) {
//            System.out.println(key +":"+ Arrays.toString(cityCoordinate.get(key)));
//        }
    }
    public void setMapAdjacency(){
        int count = 1;
        for (String city: cityCoordinate.keySet()){

//            System.out.println(count+ ". "+city) ;
//            count++;
            ArrayList <String> adjacencyList = new ArrayList<>();
            for (String[] adjacency :cityAdjacency){
                if(city.equals(adjacency[0])) {
                    if(!adjacencyList.contains(adjacency[1])) {
                        adjacencyList.add(adjacency[1]);
                    }
                }
                if(city.equals(adjacency[1])) {
                    if (!adjacencyList.contains(adjacency[0]))
                        adjacencyList.add(adjacency[0]);
                }
//                if (!adjacencyList.isEmpty())
//                    System.out.println(adjacencyList) ;
            }
            mapAdjacency.put(city,adjacencyList);
        }

    }

}
