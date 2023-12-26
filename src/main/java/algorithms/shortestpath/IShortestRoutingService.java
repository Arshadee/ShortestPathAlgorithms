package algorithms.shortestpath;

import java.util.List;
import java.util.Map;

public interface IShortestRoutingService<T> {
	Map<Integer, Integer> shortestPath(Double graph[][]);

	void displayShortestPathsMap(Map<Integer, Integer> allShortestPaths);

	Map<String, String> getShortestPaths(
		Map<Integer, String> idxCityMap,
		Map<Integer, Integer> allShortestPaths);

	Map<String, String> getShortestPaths(Map<Integer, Integer> allShortestPaths);

		String getPaths(
		String path,
		Integer key,
		Map<Integer, String> idxCityMap,
		Map<Integer, Integer> allShortestPaths);

	void displayShortestPathsList(List<String> shortestPathsList);

}