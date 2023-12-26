package algorithms.shortestpath.djikstra;

import algorithms.shortestpath.IShortestRoutingService;

import java.util.Map;

/**
 *  This application uses our DjikstraRoutingServiceImpl
 *  implements IShortestRoutingService<T[]][]>
 *
 */
public class DjikstraShortestPathApp {

    public static void main(String[] args){
       /*
        *   A 2d Double graph to be built by external class mapped from datasource
        *   using user inputs start and end nodes
        */

//      Testcase 1 from our notes
//      ref https://www.geeksforgeeks.org/djikstra-shortest-path-algorithm-in-java-using-priorityqueue
		Double graph1[][] = new Double[][] {
//       0	 1   2   3   4   5   6   7   8
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
		// Expected Shortest Path [0, 1, 7, 6, 5, 2, 8, 3, 4]

//      Testcase 2 from our notes
//      ref:   https://www.youtube.com/watch?v=pVfj6mxhdMw
		Double graph2[][] = new Double[][] {
         //  0   1   2   3   4
			{0d, 6d, 0d, 1d, 0d},
			{6D, 0d, 5d, 2d, 2d},
			{0d, 5d, 0d, 0d, 5d},
			{1d, 2d, 0d, 0d, 1d},
			{0d, 2d, 5d, 1d, 0d},
		  };
//      Expected Shortest Path [0, 3, 4, 1, 2]

//      Testcase 3 from our notes
//      ref:
		Double graph[][] = new Double[][] {
   //         0   1   2   3   4
			{ 0d, 4d, 0d, 0d, 7d },
			{ 4d, 0d, 1d, 2d, 0d },
			{ 0d, 1d, 0d, 6d, 0d },
			{ 0d, 2d, 6d, 0d, 0d },
			{ 7d, 0d, 0d, 0d, 0d },
		};
//      Expected Shortest Path [0, 1, 2, 3, 4]

        System.out.println("starting");
        IShortestRoutingService shortestRoutingService = new ShortestRoutingService();
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
