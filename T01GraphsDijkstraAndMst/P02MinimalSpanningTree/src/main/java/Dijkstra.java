import java.util.*;

public class Dijkstra {

    public static List<Integer> dijkstraAlgorithm(int[][] graph, int sourceNode, int destinationNode) {

        int[] distances = new int[graph.length];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[sourceNode] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparing(node -> distances[node]));

        queue.offer(sourceNode);

        int[] previousNodes = new int[graph.length];
        Arrays.fill(previousNodes, -1);

        boolean[] visited = new boolean[graph.length];

        while (!queue.isEmpty()) {
            int parentNode = queue.poll();
            visited[parentNode] = true;

            int[] children = graph[parentNode];

            for (int childNode = 0; childNode < children.length; childNode++) {
                if (children[childNode] != 0 && !visited[childNode]) {
                    queue.offer(childNode);

                    int newDistance = distances[parentNode] + graph[parentNode][childNode];

                    if (newDistance < distances[childNode]) {
                        distances[childNode] = newDistance;
                        previousNodes[childNode] = parentNode;
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();

        if (distances[destinationNode] == Integer.MAX_VALUE) {
            return null;
        }

        int currentNode = destinationNode;

        while (currentNode >= 0) {
            path.add(currentNode);
            currentNode = previousNodes[currentNode];
        }

        Collections.reverse(path);

        return path;
    }
}
