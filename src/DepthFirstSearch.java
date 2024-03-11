import java.util.*;

public class DepthFirstSearch {
    CityMap cityMap;

    public DepthFirstSearch(CityMap map) {
        cityMap = map;
    }

    List<String> findRoute(String src, String dest) {
        double startTime = System.nanoTime();
        Set<String> visited = new HashSet<>();
        List<String> route = dfs(src, dest, visited, new ArrayList<>());
        double endTime = System.nanoTime();
        double duration = (endTime - startTime);
        System.out.println("Time taken for searching: " + duration + " nanoseconds");
        return route;
    }

    private List<String> dfs(String current, String dest, Set<String> visited, List<String> route) {
        visited.add(current);
        route.add(current);

        if (current.equals(dest)) {
            return route;
        }

        List<String> neighbors = cityMap.mapAdjacency.get(current);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    List<String> newRoute = dfs(neighbor, dest, visited, new ArrayList<>(route));
                    if (newRoute != null) {
                        return newRoute;
                    }
                }
            }
        }
        // If no route found, backtrack
        route.remove(route.size() - 1);
        return null;
    }

}
