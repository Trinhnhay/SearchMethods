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
