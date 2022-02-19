import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static class Edge implements Comparable<Edge> {
        private final int firstNode;
        private final int secondNode;
        private final int weight;

        public Edge(int firstNode, int secondNode, int weight) {
            this.firstNode = firstNode;
            this.secondNode = secondNode;
            this.weight = weight;
        }

        public int getFirstNode() {
            return firstNode;
        }

        public int getSecondNode() {
            return secondNode;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.getWeight(), other.getWeight());
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        int numberOfNeighbourhoods = Integer.parseInt(reader.readLine());
        int numberOfDistances = Integer.parseInt(reader.readLine());
        int numberOfLightnings = Integer.parseInt(reader.readLine());

        Map<Integer, List<Edge>> graph = new HashMap<>();

        for (int i = 0; i < numberOfDistances; i++) {
            int[] edgeParts = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int firstNode = edgeParts[0];
            int secondNode = edgeParts[1];
            int distance = edgeParts[2];
            Edge newEdge = new Edge(firstNode, secondNode, distance);
            graph.putIfAbsent(firstNode, new ArrayList<>());
            graph.get(firstNode).add(newEdge);
            graph.putIfAbsent(secondNode, new ArrayList<>());
            graph.get(secondNode).add(newEdge);
        }


        boolean[] visited = new boolean[numberOfNeighbourhoods];

        Map<Integer, List<Integer>> msf = new HashMap<>();

        for (int i = 0; i < numberOfNeighbourhoods; i++) {
            prim(i, visited, graph, msf);
        }

        int[] damageInNeighbourhoods = new int[numberOfNeighbourhoods];
        for (int i = 0; i < numberOfLightnings; i++) {
            int[] lightningParts = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int startNode = lightningParts[0];
            int power = lightningParts[1];

            strikeNeighbourhoods(startNode, startNode, power, damageInNeighbourhoods, msf);
        }
        System.out.println(Arrays.stream(damageInNeighbourhoods).max().orElse(0));
    }

    private static void strikeNeighbourhoods(int neighbourhood, int previous, int power, int[] damageInNeighbourhoods, Map<Integer, List<Integer>> network) {
        if (power <= 0) {
            return;
        }
        damageInNeighbourhoods[neighbourhood] += power;
        List<Integer> nextNeighbourhoods = network.get(neighbourhood);
        if (nextNeighbourhoods != null) {
            for (Integer childNeighbourhood : nextNeighbourhoods) {
                if (childNeighbourhood != previous) {
                    strikeNeighbourhoods(childNeighbourhood, neighbourhood, power / 2, damageInNeighbourhoods, network);
                }
            }
        }
    }

    public static void prim(int node, boolean[] visited, Map<Integer, List<Edge>> graph, Map<Integer, List<Integer>> msf) {
        if (visited[node]) {
            return;
        }

        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        addAllAdjacentEdges(node, graph, priorityQueue);

        while (!priorityQueue.isEmpty()) {

            Edge edge = priorityQueue.poll();
            int firstNode = edge.getFirstNode();
            int secondNode = edge.getSecondNode();
            if (visited[firstNode] && visited[secondNode]) {
                continue;
            }

            msf.putIfAbsent(firstNode, new ArrayList<>());
            msf.get(firstNode).add(secondNode);
            msf.putIfAbsent(secondNode, new ArrayList<>());
            msf.get(secondNode).add(firstNode);

            if (!visited[firstNode]) {
                visited[firstNode] = true;
                addAllAdjacentEdges(firstNode, graph, priorityQueue);
            }

            if (!visited[secondNode]) {
                visited[secondNode] = true;
                addAllAdjacentEdges(secondNode, graph, priorityQueue);
            }
        }

    }

    private static void addAllAdjacentEdges(int node, Map<Integer, List<Edge>> graph, PriorityQueue<Edge> priorityQueue) {
        List<Edge> adjacentEdges = graph.get(node);
        if (adjacentEdges != null) {
            for (Edge edge : adjacentEdges) {
                if (node == edge.getFirstNode() || node == edge.getSecondNode()) {
                    priorityQueue.add(edge);
                }
            }
        }
    }
}
