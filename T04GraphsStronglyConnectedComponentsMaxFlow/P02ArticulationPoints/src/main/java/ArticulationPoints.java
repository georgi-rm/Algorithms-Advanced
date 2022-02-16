import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticulationPoints {

    public static List<Integer> findArticulationPoints(List<Integer>[] targetGraph) {

        List<Integer> articulationPoints = new ArrayList<>();

        boolean[] visited = new boolean[targetGraph.length];
        int[] parents = new int[targetGraph.length];
        int[] depths = new int[targetGraph.length];
        int[] lowPoints = new int[targetGraph.length];

        Arrays.fill(parents, -1);

        discoverArticulationPoints(0, 1, targetGraph, visited, parents, depths, lowPoints, articulationPoints);

        return articulationPoints;
    }

    private static void discoverArticulationPoints(int node, int currentDepth, List<Integer>[] graph, boolean[] visited, int[] parents, int[] depths, int[] lowPoints, List<Integer> articulationPoints) {
        visited[node] = true;
        depths[node] = currentDepth;
        lowPoints[node] = currentDepth;

        int countOfChildren = 0;

        boolean isArticulationPoint = false;

        for (Integer child : graph[node]) {
            if (!visited[child]) {
                parents[child] = node;
                countOfChildren++;
                discoverArticulationPoints(child, currentDepth + 1, graph, visited, parents, depths, lowPoints, articulationPoints);
                if (lowPoints[child] >= currentDepth) {
                    isArticulationPoint = true;
                }
                lowPoints[node] = Math.min(lowPoints[node], lowPoints[child]);
            } else if (parents[node] != child) {
                lowPoints[node] = Math.min(lowPoints[node], depths[child]);
            }
        }

        if ((parents[node] == -1 && countOfChildren > 1) ||
                (parents[node] != -1 && isArticulationPoint)) {
            articulationPoints.add(node);
        }
    }
}
