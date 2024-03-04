import java.util.*;

public class BreadthFirstSearch {

    private CityMap cityMap;

    public BreadthFirstSearch (CityMap city){
        cityMap=city;
    }
    public List<String> findRoute(String src, String dest) {
        double startTime = System.nanoTime();
        List<String> route = bfs(src, dest);
        double endTime = System.nanoTime();
        double duration = (endTime - startTime);
        System.out.println("Time taken for searching: " + duration + " nanoseconds");
        return route;
    }

    private List<String> bfs(String src, String dest) {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(src);
        visited.add(src);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(dest)) {
                return constructRoute(parentMap, dest);
            }
            List<String> neighbors = cityMap.mapAdjacency.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                        parentMap.put(neighbor, current);
                    }
                }
            }
        }
        return null; // No route found
    }

    private List<String> constructRoute(Map<String, String> parentMap, String dest) {
        List<String> route = new ArrayList<>();
        String current = dest;
        while (current != null) {
            route.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(route);
        return route;
    }

}
