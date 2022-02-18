import java.util.*;

public class Main {
    private static class Item implements Comparable<Item> {
        private final String name;
        private final int weight;
        private final int value;

        public Item(String name, int weight, int value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getWeight() {
            return weight;
        }

        public int getValue() {
            return value;
        }

        @Override
        public int compareTo(Item other) {
            return this.name.compareTo(other.name);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int capacity = Integer.parseInt(scanner.nextLine());

        List<Item> allItems = new ArrayList<>();
        String input = scanner.nextLine();
        while (!input.equals("end")) {
            String[] itemData = input.split("\\s+");

            String itemName = itemData[0];
            int itemWeight = Integer.parseInt(itemData[1]);
            int itemValue = Integer.parseInt(itemData[2]);
            allItems.add(new Item(itemName, itemWeight, itemValue));
            input = scanner.nextLine();
        }

        int[][] dp = new int[allItems.size() + 1][capacity + 1];
        boolean[][] takenItems = new boolean[allItems.size() + 1][capacity + 1];

        for (int itemRow = 1; itemRow <= allItems.size(); itemRow++) {
            Item item = allItems.get(itemRow - 1);

            for (int capacityColumn = 0; capacityColumn <= capacity; capacityColumn++) {

                int excluded = dp[itemRow - 1][capacityColumn];

                if (item.getWeight() > capacityColumn) {
                    dp[itemRow][capacityColumn] = excluded;
                } else {
                    int included = dp[itemRow - 1][capacityColumn - item.getWeight()] + item.getValue();

                    if (included > excluded) {
                        dp[itemRow][capacityColumn] = included;
                        takenItems[itemRow][capacityColumn] = true;
                    } else {
                        dp[itemRow][capacityColumn] = excluded;
                    }
                }
            }
        }

        int totalWeight = capacity;

        int bestValue = dp[allItems.size()][capacity];

        while (dp[allItems.size()][totalWeight - 1] == bestValue) {
            totalWeight--;
        }

        Set<Item> chosenItems = new TreeSet<>();

        int currentItem = allItems.size();
        int currentCapacity = capacity;
        while (currentItem > 0) {
            if (takenItems[currentItem][currentCapacity]) {
                Item item = allItems.get(currentItem - 1);
                chosenItems.add(item);
                currentCapacity -= item.getWeight();
            }
            currentItem--;
        }

        System.out.printf("Total Weight: %d%n", totalWeight);
        System.out.printf("Total Value: %d%n", bestValue);
        for (Item chosenItem : chosenItems) {
            System.out.println(chosenItem.getName());
        }
    }
}
