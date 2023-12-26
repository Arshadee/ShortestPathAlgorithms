package algorithms.shortestpath.djikstra;

import algorithms.shortestpath.IShortestRoutingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  This Shortest Path finder uses Djikstra Algorithm
 *  implements IShortestRoutingService<T[]][]>
 *
 *  This implementation this is stateless
 *
 *  @author arshadmayet
 */
public class DjikstraRoutingServiceImpl implements IShortestRoutingService<Double[][]> {

    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(algorithms.shortestpath.djikstra.ShortestRoutingService.class);

    public static Double INFINITY = Double.MAX_VALUE;

    public DjikstraRoutingServiceImpl() {
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
        Integer v = allShortestPaths.get(key);
        if (allShortestPaths.get(key).equals(key)) {
            return path;
        } else {
            //path = idxCityMap.get(allShortestPaths.get(key)) + "-" + path;
            path = allShortestPaths.get(key) + "-" + path;
            key = allShortestPaths.get(key);
            return getPaths(path, key, idxCityMap, allShortestPaths);
        }
    }

    public void displayShortestPathsList(List<String> shortestPathsList) {
        shortestPathsList.forEach(p -> System.out.println(p));
    }

    public void initRouting(Double[][] grid) {
        // initialise(grid);
        shortestPath(grid);
    }

//	public <T> Map<String, String> getAllOptPath(String startCityCode, T node) throws Exception {
//		try {
//			TreeMap<String, T> graphTreeMap = getOptPathsMap(startCityCode);
//			Map<Integer, Integer> allShortestPaths = getAllShortestPaths(graphTreeMap, startCityCode);
//			Map<String, String> allpathsMap = getShortestPaths(
//					graphUtilService.cityIdxMap(graphTreeMap.values(),startCityCode).getIndexCityCode(),
//					allShortestPaths
//					);
//
//
//			System.out.println("Found all optimum paths ");
//			return allpathsMap;
//		} catch (NullPointerException npe) {
//			System.out.println("Source and / or Destination City not found");
//			//throw new ApiNotFoundException(npe.getMessage(), npe);
//		}
//	}

    public String getOptPath(String startCityCode, String endCityCode) throws Exception {
        return null;
    }

    //	private <T>TreeMap<String, T> getOptPathsMap(String startCityCode, List<T> nodes) throws NullPointerException, Exception {
//		TreeMap<String, T> graphTreeMap = new TreeMap<String, T>();
//		//List<Node> nodes = (List<Node>) nodeRepository.findAll();
//		if (nodes == null || nodes.isEmpty()) {
//			throw new NullPointerException("Node does not exist");
//		}
//		Map<String, T> graphMap = nodes.stream().collect(Collectors.toMap(n -> n.getCode(), n -> n));
//		graphTreeMap.putAll(graphMap);
//
//		//to make our routing service completely stateless to beable to be called as singletons (@Service)
//		//to remove
//		Double[][] grid = graphUtilService.convertToGraph2dArray(graphTreeMap, startCityCode);
//		shortestRoutingService.initRouting(grid);
//		//end remove
//
//		return graphTreeMap;
//	}
    public static void main(String[] args) {
        // 2d int graph to be built by external class mapped from datasource
        // using user inputs start and end nodes
//		Double graph[][] = new Double[][] {
//			{0d,6d,0d,1d,0d},
//			{6D,0d,5d,2d,2d},
//			{0d,5d,0d,0d,5d},
//			{1d,2d,0d,0d,1d},
//			{0d,2d,5d,1d,0d},
//		  };

//		Double graph[][] = new Double[][] {
//			{ 0d, 4d, 0d, 0d, 7d },
//			{ 4d, 0d, 1d, 2d, 0d },
//			{ 0d, 1d, 0d, 6d, 0d },
//			{ 0d, 2d, 6d, 0d, 0d },
//			{ 7d, 0d, 0d, 0d, 0d },
//		};

/*
		Double graph[][] = new Double[][] {

			{ 0d, 6d, 0d, 1d, 0d },
			{ 6d, 0d, 5d, 2d, 0d },
			{ 0d, 5d, 0d, 0d, 5d },
			{ 1d, 2d, 0d, 0d, 1d },
			{ 0d, 2d, 5d, 1d, 0d },
		};
*/

//
        Double graph[][] = new Double[][] {
                //  0	 1  2  3  4  5  6  7  8
                {0d ,4d ,0d, 0d, 0d, 0d, 0d, 8d, 0d},
                {4d, 0d, 8d, 0d, 0d, 0d, 0d, 11d,0d},
                {0d, 8d, 0d, 7d, 0d, 4d, 0d, 0d, 2d},
                {0d, 0d, 7d, 0d, 9d, 14d,0d, 0d, 0d},
                {0d, 0d, 0d, 9d, 0d, 10d,0d, 0d, 0d},
                {0d, 0d, 4d, 14d,10d,0d, 2d, 0d, 0d},
                {0d, 0d, 0d, 0d, 0d, 2d, 0d, 1d, 6d},
                {8d, 11d,0d, 0d, 0d, 0d, 1d, 0d, 7d},
                {0d, 0d, 2d, 0d, 0d, 0d, 6d, 7d, 0d}
        };

        System.out.println("starting");
        IShortestRoutingService shortestRoutingService = new DjikstraRoutingServiceImpl();
        // shortestRoutingService.initialise(graph);
        Map<Integer, Integer> allShortestPaths = shortestRoutingService.shortestPath(graph);
        // System.out.println(shortestRoutingService);
        shortestRoutingService.displayShortestPathsMap(allShortestPaths);
        // System.out.println(getShortestPaths);
        // myDijkstra.displayShortestPaths();

        //public Map<String, String>
        System.out.println(shortestRoutingService.getShortestPaths(allShortestPaths));

    }

}

