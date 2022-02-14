import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static class Edge implements Comparable<Edge> {

        private final int startNode;
        private final int endNode;
        private final int weight;

        public Edge(int startNode, int endNode, int weight, boolean isUsed) {
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

        int budget = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int numberOfNodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int edges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        List<Edge> notConnectedEdges = new ArrayList<>();

        boolean[] visited = new boolean[numberOfNodes];

        for (int i = 0; i < edges; i++) {
            String[] edgeParameters = scanner.nextLine().split("\\s+");
            int firstNode = Integer.parseInt(edgeParameters[0]);
            int secondNode = Integer.parseInt(edgeParameters[1]);
            int weight = Integer.parseInt(edgeParameters[2]);
            boolean isConnected = edgeParameters.length > 3;

            Edge newEdge = new Edge(firstNode, secondNode, weight, isConnected);
            if (isConnected) {
                visited[firstNode] = true;
                visited[secondNode] = true;
            } else {
                notConnectedEdges.add(newEdge);
            }
        }
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        for (int node = 0; node < numberOfNodes; node++) {
            if (visited[node]) {
                addAllAdjacentEdges(node, notConnectedEdges, priorityQueue);

            }
        }

        int usedBudget = 0;
        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            int firstNode = edge.getStartNode();
            int secondNode = edge.getEndNode();
            if (!visited[firstNode] || !visited[secondNode]) {
                if (budget >= usedBudget + edge.getWeight()) {
                    usedBudget += edge.getWeight();
                    if (budget == usedBudget) {
                        break;
                    }
                    notConnectedEdges.remove(edge);

                    if (!visited[firstNode]) {
                        visited[firstNode] = true;
                        addAllAdjacentEdges(firstNode, notConnectedEdges, priorityQueue);
                    }

                    if (!visited[secondNode]) {
                        visited[secondNode] = true;
                        addAllAdjacentEdges(secondNode, notConnectedEdges, priorityQueue);
                    }
                }
            }
        }

        System.out.printf("Budget used: %d", usedBudget);

    }

    private static void addAllAdjacentEdges(int node, List<Edge> edges, PriorityQueue<Edge> priorityQueue) {
        for (Edge edge : edges) {
            if (node == edge.getStartNode() || node == edge.getEndNode()) {
                priorityQueue.add(edge);
            }
        }
    }
}
