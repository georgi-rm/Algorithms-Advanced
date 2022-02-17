import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class EdmondsKarp {
    public static int findMaxFlow(int[][] targetGraph) {
        int maxFlow = 0;
        int[] parents = new int[targetGraph.length];

        while (bfsFindPath(targetGraph, parents)) {
            int flow = Integer.MAX_VALUE;
            int node = targetGraph.length - 1;
            while (parents[node] >= 0) {
                flow = Math.min(flow, targetGraph[parents[node]][node]);
                node = parents[node];
            }

            maxFlow += flow;

            node = targetGraph.length - 1;
            while (parents[node] >= 0) {
                targetGraph[parents[node]][node] -= flow;
                node = parents[node];
            }
        }


        return maxFlow;
    }

    private static boolean bfsFindPath(int[][] graph, int[] parents) {
        Arrays.fill(parents, -1);
        boolean[] visited = new boolean[graph.length];
        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int child = 0; child < graph.length; child++) {
                if (!visited[child] && graph[node][child] > 0) {
                    queue.offer(child);
                    visited[child] = true;
                    parents[child] = node;
                }
            }
        }

        return visited[graph.length-1];
    }
}
