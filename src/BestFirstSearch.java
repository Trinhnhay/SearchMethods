import java.util.*;

public class BestFirstSearch {

    private final CityMap cityMap;

    public BestFirstSearch (CityMap map){
        cityMap = map;
    }

    public List<String> findRoute(String src, String dest) {
        double startTime = System.nanoTime();
        List<String> route = bestFirstSearch(src, dest);
        double endTime = System.nanoTime();
        double duration = (endTime - startTime);
        System.out.println("Time taken for searching: " + duration + " nanoseconds");
        return route;
    }

//public List<String> findRoute(String startCity, String endCity) {
//    PriorityQueue<Map.Entry<String, Double>> queue = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
//    Map<String, Double> heuristicMap = new HashMap<>();
//    Map<String, String> parentMap = new HashMap<>();
//    Set<String> visited = new HashSet<>();
//
//    String[] startCoords = cityMap.cityCoordinate.get(startCity);
//    String[] endCoords = cityMap.cityCoordinate.get(endCity);
//
//    queue.add(new AbstractMap.SimpleEntry<>(startCity, 0.0));
//    heuristicMap.put(startCity, 0.0);
//
//    while (!queue.isEmpty()) {
//        Map.Entry<String, Double> currentCity = queue.poll();
//        System.out.println(currentCity);
//        String cityName = currentCity.getKey();
//        visited.add(cityName);
//
//        if (cityName.equals(endCity)) {
//            List<String> shortestPath = new ArrayList<>();
//            String node = endCity;
//            while (node != null) {
//                shortestPath.add(0, node);
//                node = parentMap.get(node);
//            }
//            return shortestPath;
//        }
//
//        for (String neighbor : cityMap.mapAdjacency.get(cityName)) {
//            if (!visited.contains(neighbor)) {
//                String [] currentCoord = cityMap.cityCoordinate.get(currentCity.getKey());
//                String[] neighborCoords = cityMap.cityCoordinate.get(neighbor);
//                Distance distance = new Distance(cityMap);
//                double heuristic = distance.calculateDistance(currentCoord, neighborCoords);
//                System.out.println(heuristic);
//                double totalCost = currentCity.getValue() + heuristic;
//                if (!heuristicMap.containsKey(neighbor) || totalCost < heuristicMap.get(neighbor)) {
//                    heuristicMap.put(neighbor, totalCost);
//                    parentMap.put(neighbor, cityName);
//                    queue.add(new AbstractMap.SimpleEntry<>(neighbor, totalCost));
//                    System.out.println(queue);
//                }
//            }
//        }
//    }
//
//    return null; // No path found
//}

    public ArrayList<String> bestFirstSearch( String start, String goal) {
        PriorityQueue<Map.Entry<String, Double>> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        Map<String, String> parent = new HashMap<>();
        Map<String, Double> gScore = new HashMap<>();

        gScore.put(start, 0.0);
        priorityQueue.offer(new AbstractMap.SimpleEntry<>(start, 0.0));

        while (!priorityQueue.isEmpty()) {
            Map.Entry<String, Double> currentEntry = priorityQueue.poll();
            String currentCity = currentEntry.getKey();

            if (currentCity.equals(goal)) {
                // Reconstruct path
                ArrayList<String> path = new ArrayList<>();
                while (parent.containsKey(currentCity)) {
                    path.add(0, currentCity);
                    currentCity = parent.get(currentCity);
                }
                path.add(0, start);
                return path;
            }

            for (String neighbor : cityMap.mapAdjacency.get(currentCity)) {
                Distance distance = new Distance(cityMap);
                double tentativeGScore = gScore.get(currentCity) + distance.calculateDistance(cityMap.cityCoordinate.get(currentCity), cityMap.cityCoordinate.get(neighbor));
                if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                    parent.put(neighbor, currentCity);
                    gScore.put(neighbor, tentativeGScore);
                    double priority = tentativeGScore + distance.calculateDistance(cityMap.cityCoordinate.get(neighbor), cityMap.cityCoordinate.get(goal));
                    priorityQueue.offer(new AbstractMap.SimpleEntry<>(neighbor, priority));
                }
            }
        }

        return null; // No path found
    }
}
