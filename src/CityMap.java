import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sqrt;

public class file {

    private String adjacenciesFile = "G:\\Shared drives\\yntp\\CS461- Introduction to AI\\Assignment\\Search methods\\src\\adjacencies.txt";
    private String coordinatesFile = "G:\\Shared drives\\yntp\\CS461- Introduction to AI\\Assignment\\Search methods\\src\\coordinates.csv";

    private Map<String, String> cityAdjacency = new HashMap<>();
    private Map<String, String[]> cityCoordinate = new HashMap<>();
    private Map<Map<String, String>, Double> adjacencyList = new HashMap<>();

    public void showString (){
        try (BufferedReader reader = new BufferedReader(new FileReader(adjacenciesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] cityPair = line.split(" ");
                cityAdjacency.put(cityPair[0], cityPair[1]);
            }
//            for (String city :cityAdjacency.keySet()){
//                System.out.println(city.toString()+" "+cityAdjacency.get(city).toString());
//            }
        } catch (IOException e) {
            System.out.println("Cannot find the file");
        }
    }

    public void csvReading(){
        try (BufferedReader reader = new BufferedReader(new FileReader(coordinatesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String[] coordinate = new String[2];
                coordinate[0]=data[1];
                coordinate[1]=data[2];
                cityCoordinate.put(data[0], coordinate);
            }
//            for (String item : cityCoordinate.keySet()){
//                System.out.print(item.toString()+"  " + cityCoordinate.get(item)[0]+ " " + cityCoordinate.get(item)[0]+"\n");
//            }

        } catch (IOException e) {
            System.out.println("Cannot find the file");
        }
    }

    public void  creatingWeightMap(){
        for (String city :cityAdjacency.keySet()){
            String sourceCity = city;
            String destinationCity = cityAdjacency.get(city);

            String[] sourceCoordinate = cityCoordinate.get(sourceCity);
            String[] destinationCoordinate = cityCoordinate.get(destinationCity);
            //System.out.println(city +" "+ cityAdjacency.get(city));
          Double cityDistance = calculateDistance(sourceCoordinate,destinationCoordinate);
            System.out.println(cityDistance);
          //  adjacencyList.put(city,)
            //System.out.println(city+" " + cityAdjacency.get(city));
           //System.out.println(destinationCoordinate[0]+ " "+ destinationCoordinate[1]);
        }
    }

    private double calculateDistance (String[] source, String[] destination){
        return sqrt (Math.pow(Double.parseDouble(destination[0])-Double.parseDouble(source[0]),2)+
                        Math.pow(Double.parseDouble(destination[1])-Double.parseDouble(source[1]),2));

    }

}
