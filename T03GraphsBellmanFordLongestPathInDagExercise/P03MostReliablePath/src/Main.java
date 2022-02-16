import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfNodes = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        String[] pathElements = scanner.nextLine().split("\\s+");
        int sourceNode = Integer.parseInt(pathElements[1]);
        int destinationNode = Integer.parseInt(pathElements[3]);
        int numberOfEdges = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);

        int[][] graph = new int[numberOfNodes][numberOfNodes];

        for (int i = 0; i < numberOfEdges; i++) {
            String[] edgeElements = scanner.nextLine().split("\\s+");
            int fromNode = Integer.parseInt(edgeElements[0]);
            int toNode = Integer.parseInt(edgeElements[1]);
            int weight = Integer.parseInt(edgeElements[2]);
            graph[fromNode][toNode] = weight;
            graph[toNode][fromNode] = weight;
        }

        double[] distances = new double[numberOfNodes];
        distances[sourceNode] = 100.00;

        boolean[] visited = new boolean[numberOfNodes];
        int[] parent = new int[numberOfNodes];
        Arrays.fill(parent, -1);

        PriorityQueue<Integer> queue = new PriorityQueue<>((f, s) -> Double.compare(distances[s], distances[f]));
        queue.offer(sourceNode);

        while (!queue.isEmpty()) {
            int parentNode = queue.poll();
            visited[parentNode] = true;
            if (parentNode == destinationNode) {
                break;
            }

            for (int childNode = 0; childNode < graph[parentNode].length; childNode++) {
                int nodeWeight = graph[parentNode][childNode];
                if ( nodeWeight != 0 && !visited[childNode]) {
                    double newDistance = distances[parentNode] * nodeWeight / 100.0;

                    if (newDistance > distances[childNode]) {
                        distances[childNode] = newDistance;
                        parent[childNode] = parentNode;
                    }
                    queue.offer(childNode);
                }
            }
        }

        List<String> path = new ArrayList<>();
        path.add(String.valueOf(destinationNode));
        int node = destinationNode;
        while (parent[node] >= 0) {
            path.add(String.valueOf(parent[node]));
            node = parent[node];
        }
        Collections.reverse(path);

        System.out.printf("Most reliable path reliability: %.2f%%\n", distances[destinationNode]);
        System.out.println(String.join(" -> ", path));
    }
}
