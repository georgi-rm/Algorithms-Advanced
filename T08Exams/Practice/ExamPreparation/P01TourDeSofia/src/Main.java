import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numberOfJunctions = Integer.parseInt(reader.readLine());
        int numberOfStreets = Integer.parseInt(reader.readLine());
        int startEndJunction = Integer.parseInt(reader.readLine());

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < numberOfStreets; i++) {
            int[] edgeParts = Arrays.stream(reader.readLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            int from = edgeParts[0];
            int to = edgeParts[1];

            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
        }

        int length = findLengthOfShortestPathBfs(startEndJunction, numberOfJunctions, graph);
        System.out.println(length);
    }

    private static int findLengthOfShortestPathBfs(int startEndJunction, int numberOfJunctions, Map<Integer, List<Integer>> graph) {
        Deque<Integer> queue = new ArrayDeque<>();

        int[] distance = new int[numberOfJunctions];
        boolean[] visited = new boolean[numberOfJunctions];

        queue.offer(startEndJunction);
        visited[startEndJunction] = true;

        while (!queue.isEmpty()) {
            int parent = queue.poll();
            for (Integer child : graph.get(parent)) {
                if (!visited[child] || child == startEndJunction) {
                    visited[child] = true;
                    distance[child] = distance[parent] + 1;
                    if (child == startEndJunction) {
                        return distance[child];
                    }
                    if (graph.containsKey(child)) {
                        queue.offer(child);
                    }
                }
            }
        }
        int countOfVisitedNodes = 0;
        for (boolean isVisited : visited) {
            if (isVisited) {
                countOfVisitedNodes++;
            }
        }
        return countOfVisitedNodes;
    }
}
