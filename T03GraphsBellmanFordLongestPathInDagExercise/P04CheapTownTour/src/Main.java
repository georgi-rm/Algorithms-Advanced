import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int towns = Integer.parseInt(scanner.nextLine());
        int numberOfRoads = Integer.parseInt(scanner.nextLine());

        int[][] graph = new int[towns][towns];

        for (int i = 0; i < numberOfRoads; i++) {
            String[] readElements = scanner.nextLine().split("\\s+");
            int firstTown = Integer.parseInt(readElements[0]);
            int secondTown = Integer.parseInt(readElements[2]);
            int cost = Integer.parseInt(readElements[4]);
            graph[firstTown][secondTown] = cost;
            graph[secondTown][firstTown] = cost;
        }
        int cost = kruscal(graph);

        System.out.printf("Total cost: %d", cost);
    }

    private static Integer kruscal(int[][] graph) {
        int totalCost = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(e -> graph[e[0]][e[1]]));

        int[] parent = new int[graph.length];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int row = 0; row < graph.length; row++) {
            for (int col = 0; col < graph[row].length; col++) {
                if (graph[row][col] != 0) {
                    queue.offer(new int[]{row, col});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] edge = queue.poll();
            if (parent[edge[0]] != parent[edge[1]]) {
                totalCost += graph[edge[0]][edge[1]];
                int secondRoot = parent[edge[1]];
                parent[edge[1]] = parent[edge[0]];
                for (int i = 0; i < parent.length; i++) {
                    if (parent[i] == secondRoot) {
                        parent[i] = parent[edge[0]];
                    }
                }
            }
        }

        return totalCost;
    }
}
