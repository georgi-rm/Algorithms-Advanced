import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static class Edge implements Comparable<Edge> {

        private final int startNode;
        private final int endNode;
        private final int weight;

        public Edge(int startNode, int endNode, int weight) {
            this.startNode = startNode;
            this.endNode = endNode;
            this.weight = weight;
        }

        public int getStartNode() {
            return this.startNode;
        }

        public int getEndNode() {
            return this.endNode;
        }

        public int getWeight() {
            return this.weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.getWeight());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfNodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        List<Edge> allEdges = new ArrayList<>();

        for (int i = 0; i < edges; i++) {
            String[] edgeParameters = scanner.nextLine().split("\\s+");
            int firstNode = Integer.parseInt(edgeParameters[0]);
            int secondNode = Integer.parseInt(edgeParameters[1]);
            int weight = Integer.parseInt(edgeParameters[2]);

            allEdges.add(new Edge(firstNode, secondNode, weight));
        }

        List<Edge> minimumSpanningTree = kruskal(numberOfNodes, allEdges);
        int totalWeight = 0;

        for (Edge edge : minimumSpanningTree) {
            totalWeight += edge.getWeight();
        }

        System.out.printf("Minimum spanning forest weight: %d", totalWeight);
    }

    public static List<Edge> kruskal(int numberOfVertices, List<Edge> edges) {

        List<Edge> minimalSpanningTree = new ArrayList<>();
        Collections.sort(edges);

        int[] parents = new int[numberOfVertices];

        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }

        for (Edge edge : edges) {
            int firstRoot = parents[edge.getStartNode()];
            int secondRoot = parents[edge.getEndNode()];

            if (firstRoot != secondRoot) {
                minimalSpanningTree.add(edge);
                parents[secondRoot] = firstRoot;
                for (int node = 0; node < parents.length; node++) {
                    if (parents[node] == secondRoot) {
                        parents[node] = firstRoot;
                    }
                }
            }
        }

        Collections.sort(minimalSpanningTree);
        return minimalSpanningTree;
    }
}
