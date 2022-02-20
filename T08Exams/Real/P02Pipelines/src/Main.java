import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfAgents = Integer.parseInt(scanner.nextLine());
        int numberOfPipelines = Integer.parseInt(scanner.nextLine());

        Map<String, Integer> agentsNames = new HashMap<>();
        Map<String, Integer> pipelinesNames = new HashMap<>();

        for (int agentId = 0; agentId < numberOfAgents; agentId++) {
            agentsNames.putIfAbsent(scanner.nextLine(), agentId);
        }

        for (int pipelineId = 0; pipelineId < numberOfPipelines; pipelineId++) {
            pipelinesNames.putIfAbsent(scanner.nextLine(), pipelineId);
        }

        int numberOfNodes = numberOfAgents + numberOfPipelines + 2;
        int[][] graph = new int[numberOfNodes][numberOfNodes];
        int startNode = graph.length - 2;

        for (int agentId = 0; agentId < numberOfAgents; agentId++) {
            graph[startNode][agentId] = 1;
        }

        for (int i = 0; i < numberOfAgents; i++) {
            String[] connectionParts = scanner.nextLine().split(", ");
            int agentId = agentsNames.get(connectionParts[0]);
            for (int j = 1; j < connectionParts.length; j++) {
                String pipelineName = connectionParts[j];
                graph[agentId][numberOfAgents + pipelinesNames.get(pipelineName)] = 1;
            }
        }

        for (int pipelineId = 0; pipelineId < numberOfPipelines; pipelineId++) {
            graph[numberOfAgents + pipelineId][graph.length-1] = 1;
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
        for (int i = 0; i < numberOfAgents; ++i)
            for (int j = 0; j < numberOfPipelines; ++j)
                if (graph[numberOfAgents + j][i] > 0)
                    stringBuilder.append(getNameByIndex(i, agentsNames)).append(" - ").append(getNameByIndex(j, pipelinesNames)).append('\n');
        System.out.println(stringBuilder.toString().trim());

    }

    private static String getNameByIndex(int index, Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return "";
    }

    private static boolean bfsFindPath(int[][] graph, int[] parents, int startNode) {
        Arrays.fill(parents, -1);
        boolean[] visited = new boolean[graph.length];
        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(startNode);
        visited[startNode] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int child = 0; child < graph.length; child++) {
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
