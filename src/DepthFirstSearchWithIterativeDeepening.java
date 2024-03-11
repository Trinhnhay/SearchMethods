import java.util.*;

public class DepthFirstSearchWithIterativeDeepening {

    private final CityMap cityMap;

    public DepthFirstSearchWithIterativeDeepening(CityMap map){
        cityMap = map;
    }

    public List<String> findRoute(String src, String dest) {
        long startTime = System.nanoTime();
        List<String> route = iterativeDeepeningDFS(src, dest);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Time taken for searching: " + duration + " nanoseconds");
        return route;
    }

    private List<String> iterativeDeepeningDFS(String src, String dest) {
        int maxDepth = 0;
        while (true) {
            Set<String> visited = new HashSet<>();
            List<String> route = depthLimitedDFS(src, dest, maxDepth, visited);
            if (route != null) {
                return route;
            }
            maxDepth++;
        }
    }

    private List<String> depthLimitedDFS(String current, String dest, int maxDepth, Set<String> visited) {
        return depthLimitedDFSHelper(current, dest, maxDepth, visited, new ArrayList<>());
    }

    private List<String> depthLimitedDFSHelper(String current, String dest, int maxDepth, Set<String> visited, List<String> route) {
        if (current.equals(dest)) {
            route.add(current);
            return route;
        }

        if (maxDepth <= 0) {
            return null;
        }

        visited.add(current);
        route.add(current);

        List<String> neighbors = cityMap.mapAdjacency.get(current);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    List<String> result = depthLimitedDFSHelper(neighbor, dest, maxDepth - 1, visited, route);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        // Backtrack
        route.remove(route.size() - 1);
        visited.remove(current);
        return null;
    }
}


