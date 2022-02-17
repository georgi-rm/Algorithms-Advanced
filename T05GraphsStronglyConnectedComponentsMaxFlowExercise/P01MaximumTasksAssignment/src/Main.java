import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfPeople = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        int numberOfTasks = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        int numberOfNodes = numberOfPeople + numberOfTasks + 2;
        int[][] graph = new int[numberOfNodes][numberOfNodes];
        int startNode = graph.length - 2;

        for (int person = 0; person < numberOfPeople; person++) {
            String tasksString = scanner.nextLine();
            for (int task = 0; task < numberOfTasks; task++) {
                graph[person][numberOfPeople + task] = tasksString.charAt(task) == 'Y' ? 1 : 0;
            }
            graph[startNode][person] = 1;
        }

        for (int task = 0; task < numberOfTasks; task++) {
            graph[numberOfPeople + task][graph.length-1] = 1;
        }

        int[] parents = new int[graph.length];
        while (bfsFindPath(graph, parents, startNode)) {
            int node = graph.length - 1;
            while (parents[node] >= 0) {
                graph[parents[node]][node] = 0;
                graph[node][parents[node]] = 1;
                node = parents[node];
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numberOfPeople; ++i)
            for (int j = 0; j < numberOfTasks; ++j)
                if (graph[numberOfPeople + j][i] > 0)
                    stringBuilder.append((char) ('A' + i)).append('-').append(j + 1).append('\n');
        System.out.println(stringBuilder.toString().trim());

    }

    private static boolean bfsFindPath(int[][] graph, int[] parents, int startNode) {
        Arrays.fill(parents, -1);
        boolean[] visited = new boolean[graph.length];
        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(startNode);
        visited[startNode] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int child = graph.length - 1; child >= 0; child--) {
                if (!visited[child] && graph[node][child] > 0) {
                    queue.offer(child);
                    visited[child] = true;
                    parents[child] = node;
                }
            }
        }

        return visited[graph.length-1];
    }
}
