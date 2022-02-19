import java.util.*;

public class Main {
    private static class Item {
        private final int weight;
        private final int value;

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        public int getWeight() {
            return weight;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] values = Arrays.stream(scanner.nextLine().split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] weights = Arrays.stream(scanner.nextLine().split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();

        List<Item> allItems = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            allItems.add(new Item(weights[i], values[i]));
        }

        int capacity = Integer.parseInt(scanner.nextLine());

        int[][] dp = new int[allItems.size() + 1][capacity + 1];

        for (int itemRow = 1; itemRow <= allItems.size(); itemRow++) {
            Item item = allItems.get(itemRow - 1);

            for (int capacityColumn = 0; capacityColumn <= capacity; capacityColumn++) {

                int excluded = dp[itemRow - 1][capacityColumn];

                if (item.getWeight() > capacityColumn) {
                    dp[itemRow][capacityColumn] = excluded;
                } else {
                    int included = dp[itemRow - 1][capacityColumn - item.getWeight()] + item.getValue();

                    dp[itemRow][capacityColumn] = Math.max(included, excluded);
                }
            }
        }

        System.out.printf("Maximum value: %d", dp[allItems.size()][capacity]);
    }
}
