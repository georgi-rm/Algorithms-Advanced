import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class KruskalAlgorithm {

    public static List<Edge> kruskal(int numberOfVertices, List<Edge> edges) {
        boolean[] visited = new boolean[numberOfVertices];

        List<Edge> minimalSpanningTree = new ArrayList<>();

        for (int i = 0; i < numberOfVertices; i++) {
            prim(i, visited, edges, minimalSpanningTree);
        }

        Collections.sort(minimalSpanningTree);
        return minimalSpanningTree;
    }

    public static void prim(int node, boolean[] visited, List<Edge> edges, List<Edge> minimalSpanningTree) {
        if (visited[node]) {
            return;
        }

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        addAllAdjacentEdges(node, edges, priorityQueue);

        while (!priorityQueue.isEmpty()) {

            Edge edge = priorityQueue.poll();
            int firstNode = edge.getStartNode();
            int secondNode = edge.getEndNode();
            if (!visited[firstNode] || !visited[secondNode]) {
                minimalSpanningTree.add(edge);
            }

            if (!visited[firstNode]) {
                visited[firstNode] = true;
                addAllAdjacentEdges(firstNode, edges, priorityQueue);
            }

            if (!visited[secondNode]) {
                visited[secondNode] = true;
                addAllAdjacentEdges(secondNode, edges, priorityQueue);
            }
        }

    }

    private static void addAllAdjacentEdges(int node, List<Edge> edges, PriorityQueue<Edge> priorityQueue) {
        for (Edge edge : edges) {
            if (node == edge.getStartNode() || node == edge.getEndNode()) {
                priorityQueue.add(edge);
            }
        }
    }
}
