package algorithms.shortestpath.djikstra;

import algorithms.shortestpath.IShortestRoutingService;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  This Shortest Path finder uses Djikstra Algorithm
 *  implements IShortestRoutingService<T[]][]>
 *
 *  This implementation this is stateless
 *
 *  @author arshadmayet
 */
public class ShortestRoutingService implements IShortestRoutingService<Double[][]> {

    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(algorithms.shortestpath.djikstra.ShortestRoutingService.class);

    public static Double INFINITY = Double.MAX_VALUE;

    public ShortestRoutingService() {
    }

    private void init(Double[][] graph, List<Integer> unVisited, List<String> vertices,
                      Map<Integer, Double> vertxAndWeight) {
        for (int i = 0; i < graph.length; i++) {
            unVisited.add(i);
            vertices.add(i + "");
            vertxAndWeight.put(i, INFINITY);
        }
    }

    private Integer getNextLowestUnvisitedNode(Map<Integer, Double> vertxAndWeight, List<Integer> unVisited) {
        Map<Integer, Double> unVisitedMap = vertxAndWeight.entrySet().stream()
                .filter(v -> unVisited.contains(v.getKey()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

        Double lowestWeight = INFINITY;
        Integer lowestWeightIdx = -1;
        for (Map.Entry<Integer, Double> entry : unVisitedMap.entrySet()) {
            if (entry.getValue() < lowestWeight) {
                lowestWeight = entry.getValue();
                lowestWeightIdx = entry.getKey();
            }
        }
        return lowestWeightIdx;
    }

    public Map<Integer, Integer> shortestPath(Double graph[][]) {

        Map<Integer, Double> vertxAndWeight = new HashMap<>();
        Map<Integer, Integer> allShortestPaths = new HashMap<>();
        Set<String> visited = new LinkedHashSet<>();
        List<Integer> unVisited = new ArrayList<>();// must be ordered
        List<String> vertices = new ArrayList<>();

        init(graph, unVisited, vertices, vertxAndWeight);
        vertxAndWeight.put(0, 0.0);
        allShortestPaths.put(0, 0);
        int currentNodeIdx = 0; // A
        Double fromStartWeight = 0.0;

        while (!unVisited.isEmpty()) {
            int smallestUnvisitedNode = 0;
            for (int i = 0; i < graph.length; i++) {

                // get immediate neighbors for current node
                boolean isNeighbour = (graph[currentNodeIdx][i] != 0d);
                boolean notVisited = (unVisited.contains(i));
                if (isNeighbour && notVisited) {
                    // check if weight in our vertextWeight Map is bigger than weight to neighbour from start
                    // update vertex in vertextWeight if it bigger.
                    Double vertxAndWeightGetIdx = vertxAndWeight.get(i);
                    Double grphCnIdxSw = (graph[currentNodeIdx][i] + fromStartWeight);
                    if (vertxAndWeightGetIdx > grphCnIdxSw) {
                        vertxAndWeight.put(i, graph[currentNodeIdx][i] + fromStartWeight);
                        allShortestPaths.put(i, currentNodeIdx);
                    }
                }
                smallestUnvisitedNode = getNextLowestUnvisitedNode(vertxAndWeight, unVisited);
                fromStartWeight = vertxAndWeight.get(smallestUnvisitedNode);
            }

            visited.add(currentNodeIdx + "");
            unVisited.remove(Integer.valueOf(currentNodeIdx));
            currentNodeIdx = smallestUnvisitedNode;
        }
        System.out.println("vertxAndWeight " + vertxAndWeight);
        System.out.println("visited " + visited);
        System.out.println("unVisited " + unVisited);
        return allShortestPaths;
    }

    public void displayShortestPathsMap(Map<Integer, Integer> allShortestPaths) {
        // LOGGER.info("Shortest paths Map",allShortestPaths);
        System.out.println(allShortestPaths);
    }

    public Map<String, String> getShortestPaths(Map<Integer, String> idxCityMap,
                                                Map<Integer, Integer> allShortestPaths) {
        Map<String, String> shortestPathsMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : allShortestPaths.entrySet()) {
            String path = getPaths(idxCityMap.get(entry.getKey()) + "-", entry.getKey(), idxCityMap, allShortestPaths);
            path = path.substring(0, path.length() - 1);
            shortestPathsMap.put(idxCityMap.get(entry.getKey()), path);
        } // end for

        return shortestPathsMap;
    }

    public Map<String, String> getShortestPaths(Map<Integer, Integer> allShortestPaths) {
        Map<String, String> shortestPathsMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : allShortestPaths.entrySet()) {
            String path = getPaths(entry.getKey() + "-", entry.getKey(), null, allShortestPaths);
            path = path.substring(0, path.length() - 1);
            shortestPathsMap.put(entry.getKey()+"", path);
        } // end for

        return shortestPathsMap;
    }

    public String getPaths(String path, Integer key, Map<Integer, String> idxCityMap,
                           Map<Integer, Integer> allShortestPaths) {
        if (allShortestPaths.get(key).equals(key)) {
            return path;
        } else {
            path = allShortestPaths.get(key) + "-" + path;
            key = allShortestPaths.get(key);
            return getPaths(path, key, idxCityMap, allShortestPaths);
        }
    }

    public void displayShortestPathsList(List<String> shortestPathsList) {
        shortestPathsList.forEach(p -> System.out.println(p));
    }

    public void initRouting(Double[][] grid) {
        shortestPath(grid);
    }


    public String getOptPath(String startCityCode, String endCityCode) throws Exception {
        return null;
    }

}

