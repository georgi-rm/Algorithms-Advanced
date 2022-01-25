import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalAlgorithm {

    public static List<Edge> kruskal(int numberOfVertices, List<Edge> edges) {

        List<Edge> minimalSpanningTree = new ArrayList<>();
        Collections.sort(edges);

        int[] parents = new int[numberOfVertices];

        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }

        for (Edge edge : edges) {
            int firstRoot = findRoot(edge.getStartNode(), parents);
            int secondRoot = findRoot(edge.getEndNode(), parents);

            if (firstRoot != secondRoot) {
                minimalSpanningTree.add(edge);
                parents[secondRoot] = firstRoot;
            }
        }


        return minimalSpanningTree;
    }

    public static int findRoot(int node, int[] parents) {

        while (parents[node] != node) {
            node = parents[node];
        }

        return node;
    }
}
