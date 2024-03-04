import java.util.*;

public class AStartSearch {

    private CityMap cityMap;

    public AStartSearch (CityMap map){
        cityMap = map;
    }

    public List<String> findRoute(String start, String goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(node -> node.fCost));
        Set<String> closedSet = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();
        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();

        // Initialize scores
        gScore.put(start, 0.0);
        fScore.put(start, calculateHeuristic(cityMap.cityCoordinate.get(start), cityMap.cityCoordinate.get(goal)));

        // Add start node to open set
        openSet.add(new Node(start, fScore.get(start)));

        double startTime = System.nanoTime();

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.city.equals(goal)) {
                // Reconstruct path
                List<String> path = new ArrayList<>();
                String node = goal;
                while (node != null) {
                    path.add(0, node);
                    node = parentMap.get(node);
                }

                double endTime = System.nanoTime();
                System.out.println("Time of searching: " + (endTime - startTime) + " nanoseconds");

                return path;
            }

            closedSet.add(current.city);

            for (String neighbor : cityMap.mapAdjacency.get(current.city)) {
                if (closedSet.contains(neighbor)) {
                    continue; // Ignore already evaluated neighbor
                }
                Distance distance = new Distance(cityMap);
                double tentativeGScore = gScore.getOrDefault(current.city, Double.MAX_VALUE) +
                        distance.calculateDistance(cityMap.cityCoordinate.get(current.city), cityMap.cityCoordinate.get(neighbor));

                if (tentativeGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    parentMap.put(neighbor, current.city);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + calculateHeuristic(cityMap.cityCoordinate.get(neighbor), cityMap.cityCoordinate.get(goal)));
                    openSet.add(new Node(neighbor, fScore.get(neighbor)));
                }
            }
        }

        // No path found
        return Collections.emptyList();
    }

//    private double calculateDistance(double[] coord1, double[] coord2) {
//        double lat1 = Math.toRadians(coord1[0]);
//        double lon1 = Math.toRadians(coord1[1]);
//        double lat2 = Math.toRadians(coord2[0]);
//        double lon2 = Math.toRadians(coord2[1]);
//
//        double dLat = lat2 - lat1;
//        double dLon = lon2 - lon1;
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(lat1) * Math.cos(lat2) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return RADIUS_OF_EARTH * c;
//    }


    private double calculateHeuristic(String[] coord1, String[] coord2) {
        double latDiff = Double.parseDouble(coord2[0]) - Double.parseDouble(coord1[0]);
        double lonDiff = Double.parseDouble(coord2[1]) - Double.parseDouble(coord1[1]);

        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff); // Euclidean distance
    }

    private static class Node {
        String city;
        double fCost;

        Node(String city, double fCost) {
            this.city = city;
            this.fCost = fCost;
        }
    }
}
