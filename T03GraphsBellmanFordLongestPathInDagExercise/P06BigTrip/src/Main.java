import java.util.*;

public class Main {
    private static class Edge {
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

        Map<Integer, List<Edge>> graph = new HashMap<>();

        for (int i = 1; i <= numberOfNodes; i++) {
            graph.putIfAbsent(i, new ArrayList<>());
        }
        for (int i = 0; i < numberOfEdges; i++) {
            int[] edgeParameters = Arrays.stream(scanner.nextLine().split("\\s+")).
                    mapToInt(Integer::parseInt)
                    .toArray();
            int from = edgeParameters[0];
            int to = edgeParameters[1];
            int weight = edgeParameters[2];

            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(new Edge(from, to, weight));
        }

        int source = Integer.parseInt(scanner.nextLine());
        int destination = Integer.parseInt(scanner.nextLine());

        Set<Integer> visited = new HashSet<>();
        List<Integer> cycles = new ArrayList<>();
        List<Integer> topologicallySorted = new ArrayList<>();

        dfsTopologicalSort(1, topologicallySorted, graph, visited, cycles);

        int[] distance = new int[numberOfNodes + 1];
        Arrays.fill(distance, Integer.MIN_VALUE);
        distance[source] = 0;

        int[] parentNodes = new int[numberOfNodes+1];
        Arrays.fill(parentNodes, -1);
        for (Integer node : topologicallySorted) {
            for (Edge edge : graph.get(node)) {
                if (distance[node] != Integer.MIN_VALUE) {
                    int newWeight = distance[node] + edge.getWeight();
                    if (newWeight > distance[edge.getDestination()]) {
                        distance[edge.getDestination()] = newWeight;
                        parentNodes[edge.getDestination()] = node;
                    }
                }
            }
        }

        System.out.println(distance[destination]);
        Deque<Integer> stack = new ArrayDeque<>();
        int node = destination;
        while (node > 0) {
            stack.push(node);
            node = parentNodes[node];
        }

        while (!stack.isEmpty()) {
            System.out.printf("%d ", stack.pop());
        }
    }

    private static void dfsTopologicalSort(Integer node, List<Integer> topologicallySorted, Map<Integer, List<Edge>> graph, Set<Integer> visited, List<Integer> cycles) {
        if (cycles.contains(node)) {
            return;
        }

        if (visited.contains(node)) {
            return;
        }

        cycles.add(node);
        visited.add(node);

        for (Edge edge : graph.get(node)) {
            dfsTopologicalSort(edge.getDestination(), topologicallySorted, graph, visited, cycles);
        }

        topologicallySorted.add(0, node);
        cycles.remove(node);
    }

}
