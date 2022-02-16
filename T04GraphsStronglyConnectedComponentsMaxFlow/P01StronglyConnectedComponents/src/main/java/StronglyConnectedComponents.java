import java.util.*;

public class StronglyConnectedComponents {

    public static List<List<Integer>> findStronglyConnectedComponents(List<Integer>[] targetGraph) {

        List<List<Integer>> stronglyConnectedComponents = new ArrayList<>();
        boolean[] visited = new boolean[targetGraph.length];
        Deque<Integer> stack = new ArrayDeque<>();
        List<List<Integer>> reversedGraph = new ArrayList<>();

        for (int node = 0; node < targetGraph.length; node++) {
            dfs(node, targetGraph, visited, stack);
        }

        buildReverseGraph(targetGraph, reversedGraph);

        Arrays.fill(visited, false);

        while (!stack.isEmpty()) {
            int node = stack.pop();

            if (!visited[node]) {
                List<Integer> connectedComponent = new ArrayList<>();
                reversedDfs(node, reversedGraph, visited, connectedComponent);
                stronglyConnectedComponents.add(connectedComponent);
            }

        }

        return stronglyConnectedComponents;
    }

    private static void reversedDfs(int node, List<List<Integer>> reversedGraph, boolean[] visited, List<Integer> connectedComponent) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;
        connectedComponent.add(node);
        for (Integer childNode : reversedGraph.get(node)) {
            reversedDfs(childNode, reversedGraph, visited, connectedComponent);
        }
    }

    private static void buildReverseGraph(List<Integer>[] targetGraph, List<List<Integer>> reversedGraph) {
        for (int i = 0; i < targetGraph.length; i++) {
            reversedGraph.add(new ArrayList<>());
        }

        for (int parentNode = 0; parentNode < targetGraph.length; parentNode++) {
            for (Integer child : targetGraph[parentNode]) {
                reversedGraph.get(child).add(parentNode);
            }
        }
    }

    private static void dfs(int node, List<Integer>[] graph, boolean[] visited, Deque<Integer> stack) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;

        for (Integer child : graph[node]) {
            dfs(child, graph, visited, stack);
        }

        stack.push(node);
    }
}
