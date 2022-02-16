import java.util.*;

public class Main {
    public static class Edge {
        private final int source;
        private final int destination;
        private final int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        public int getSource() {
            return source;
        }

        public int getDestination() {
            return destination;
        }

        public int getWeight() {
            return weight;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfNodes = Integer.parseInt(scanner.nextLine());
        int numberOfEdges = Integer.parseInt(scanner.nextLine());

        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < numberOfEdges; i++) {
            int[] edgeParameters = Arrays.stream(scanner.nextLine().split("\\s+")).
                    mapToInt(Integer::parseInt)
                    .toArray();
            edges.add(new Edge(edgeParameters[0], edgeParameters[1], edgeParameters[2]));
        }

        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        int[] parent = new int[numberOfNodes + 1];
        Arrays.fill(parent, -1);

        int[] distance = new int[numberOfNodes + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        for (int i = 0; i < numberOfNodes - 1; i++) {
            bellmanFord(parent, distance, edges, false);
        }

        try {
            bellmanFord(parent, distance, edges, true);
            ArrayDeque<Integer> shortestPath = new ArrayDeque<>();
            shortestPath.push(destination);
            int node = destination;
            while (parent[node] >= 0) {
                node = parent[node];
                shortestPath.push(node);
            }
            while (!shortestPath.isEmpty()) {
                System.out.printf("%d ", shortestPath.pop());
            }
            System.out.println();
            System.out.println(distance[destination]);
        }catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void bellmanFord(int[] parent, int[] distance, List<Edge> edges, boolean checkForNegativeCycle) {
        for (Edge edge : edges) {
            if (distance[edge.getSource()] != Integer.MAX_VALUE) {
                int newDistance = distance[edge.getSource()] + edge.getWeight();
                if (newDistance < distance[edge.getDestination()]) {
                    if (checkForNegativeCycle) {
                        throw new IllegalStateException("Undefined");
                    }
                    distance[edge.getDestination()] = newDistance;
                    parent[edge.getDestination()] = edge.getSource();
                }
            }
        }
    }
}
