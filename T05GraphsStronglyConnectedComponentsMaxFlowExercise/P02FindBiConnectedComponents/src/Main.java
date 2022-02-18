import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static int[] depths;
    private static int[] lowPoints;
    private static int[] parents;
    private static final List<List<Integer>> result = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner( System.in);

        int numberOfNodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int numberOfEdges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        depths = new int[numberOfNodes];
        lowPoints = new int[numberOfNodes];
        parents = new int[numberOfNodes];

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            graph.add(new ArrayList<>());
        }

        for (int edge = 0; edge < numberOfEdges; edge++) {
            int[] nodesOfEdge = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            graph.get(nodesOfEdge[0]).add(nodesOfEdge[1]);
            graph.get(nodesOfEdge[1]).add(nodesOfEdge[0]);
        }

        findBiConnectedComponents(0, 1, graph, new ArrayList<>());
        System.out.printf("Number of bi-connected components: %d", result.size());
    }

    private static void findBiConnectedComponents(int node, int depth, List<List<Integer>> graph, List<Integer> subComponent) {
        depths[node] = depth;
        lowPoints[node] = depth;

        for (Integer child : graph.get(node)) {
            if (depths[child] == 0) {
                parents[child] = node;
                List<Integer> childComponent = new ArrayList<>();
                findBiConnectedComponents(child, depth + 1, graph, childComponent);
                if (lowPoints[child] >= depths[node] || parents[node] == -1) {
                    childComponent.add(node);
                    result.add(childComponent);
                } else {
                    subComponent.addAll(childComponent);
                }
                lowPoints[node] = Math.min(lowPoints[node], lowPoints[child]);
            } else {
                lowPoints[node] = Math.min(lowPoints[node], depths[child]);
            }
        }
        subComponent.add(node);
    }
}
