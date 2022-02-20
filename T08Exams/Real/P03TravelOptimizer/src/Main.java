import java.util.*;

public class Main {
    public static class Edge {

        private final int firstNode;
        private final int secondNode;
        private final int weight;

        public Edge(int startNode, int endNode, int weight) {
            this.firstNode = startNode;
            this.secondNode = endNode;
            this.weight = weight;
        }

        public int getFirstNode() {
            return this.firstNode;
        }

        public int getSecondNode() {
            return this.secondNode;
        }

        public int getWeight() {
            return this.weight;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfEdges = Integer.parseInt(scanner.nextLine());

        List<Edge> graph = new ArrayList<>();

        for (int edge = 0; edge < numberOfEdges; edge++) {
            int[] edgeParameters = Arrays.stream(scanner.nextLine().split(", "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int edgeFirstNode = edgeParameters[0];
            int edgeSecondNode = edgeParameters[1];
            int edgeWeight = edgeParameters[2];
            graph.add(new Edge(edgeFirstNode, edgeSecondNode, edgeWeight));
        }

        int startNode = Integer.parseInt(scanner.nextLine());
        int endNode = Integer.parseInt(scanner.nextLine());
        int numberOfStops = Integer.parseInt(scanner.nextLine());

        List<Integer> shortestPath = dijkstra(graph, startNode, endNode, numberOfStops);

        if (shortestPath.size() == 0) {
            System.out.println("There is no such path.");
        } else {
            for (Integer integer : shortestPath) {
                System.out.printf("%d ", integer);
            }
        }
    }


    public static List<Integer> dijkstra(List<Edge> graph, int sourceNode, int destinationNode, int maxStops) {

        Map<Integer, Integer> distances = new HashMap<>();

        for (Edge edge : graph) {
            distances.putIfAbsent(edge.getFirstNode(), Integer.MAX_VALUE);
            distances.putIfAbsent(edge.getSecondNode(), Integer.MAX_VALUE);
        }

        distances.put(sourceNode, 0);

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparing(distances::get));

        queue.offer(sourceNode);


        Map<Integer, Integer> previousNodes = new HashMap<>();
        for (Edge edge : graph) {
            previousNodes.putIfAbsent(edge.getFirstNode(), -1);
            previousNodes.putIfAbsent(edge.getSecondNode(), -1);
        }

        Map<Integer, Boolean> visited = new HashMap<>();
        for (Edge edge : graph) {
            visited.putIfAbsent(edge.getFirstNode(), false);
            visited.putIfAbsent(edge.getSecondNode(), false);
        }

        Map<Integer, Integer> numberOfStops = new HashMap<>();
        for (Edge edge : graph) {
            numberOfStops.putIfAbsent(edge.getFirstNode(), 0);
            numberOfStops.putIfAbsent(edge.getSecondNode(), 0);
        }

        while (!queue.isEmpty()) {
            int parentNode = queue.poll();
            visited.put(parentNode, true);

            if (parentNode == destinationNode) {
                break;
            }
            for (Edge edge : graph) {
                if (edge.getFirstNode() == parentNode || edge.getSecondNode() == parentNode) {
                    int childNode;
                    if (edge.getFirstNode() == parentNode) {
                        childNode = edge.getSecondNode();
                    } else {
                        childNode = edge.getFirstNode();
                    }

                    if (!visited.get(childNode) && (numberOfStops.get(parentNode) < maxStops ||( numberOfStops.get(parentNode) == maxStops && childNode == destinationNode))) {
                        queue.offer(childNode);

                        int newDistance = distances.get(parentNode) + edge.getWeight();

                        if (newDistance < distances.get(childNode)) {
                            distances.put(childNode, newDistance);
                            previousNodes.put(childNode, parentNode);
                            numberOfStops.put(childNode, numberOfStops.get(parentNode) + 1);
                        }
                    }
                }
            }
        }

        List<Integer> path = new ArrayList<>();

        if (distances.get(destinationNode) == Integer.MAX_VALUE) {
            return path;
        }

        int currentNode = destinationNode;

        while (currentNode >= 0) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }

        Collections.reverse(path);

        return path;
    }
}
