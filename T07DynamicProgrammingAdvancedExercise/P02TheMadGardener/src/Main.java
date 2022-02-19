import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static class DpSequence {
        private int size;
        private int prev;
        private int sum;

        public int getSize() {
            return size;
        }

        public int getPrev() {
            return prev;
        }

        public int getSum() {
            return sum;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] input = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] plants = new int[input.length + 1];

        System.arraycopy(input, 0, plants, 1, plants.length - 1);

        DpSequence[] firstLIS = getLIS(plants);

        int[] reversed = new int[plants.length];

        for (int i = 1; i < input.length; i++) {
            reversed[i] = plants[(plants.length - 1) - i + 1];
        }

        DpSequence[] secondLIS = getLIS(reversed);

        int maxSize = 0;
        int maxSum = 0;
        int peak = 0;

        for (int i = 1; i < plants.length; i++) {
            int size = firstLIS[i].getSize() + secondLIS[plants.length - i].getSize();
            if (size >= maxSize) {
                maxSize = size;
                maxSum = firstLIS[i].getSum() + secondLIS[plants.length - i].getSum() - plants[i];
                peak = i;
            }
        }

        int[] result = new int[plants.length];

        int element = firstLIS[peak].getSize();

        int index = peak;

        int nextElement = 0;

        while (index != 0) {
            nextElement++;
            result[element--] = plants[index];
            index = firstLIS[index].getPrev();
        }

        index = secondLIS[plants.length - peak].getPrev();

        while (index != 0) {
            result[++nextElement] = reversed[index];
            index = secondLIS[index].getPrev();
        }

        for (int i = 1; i < maxSize; i++) {
            System.out.printf("%d ", result[i]);
        }

        System.out.println();
        System.out.printf("%.2f%n", 1.0 * maxSum / (maxSize - 1));

        System.out.println(maxSize - 1);
    }

    private static DpSequence[] getLIS(int[] plants) {
        DpSequence[] lis = new DpSequence[plants.length];

        lis[0] = new DpSequence();

        for (int i = 1; i <= plants.length - 1; i++) {
            lis[i] = new DpSequence();
            for (int j = 0; j < i; j++) {
                if (plants[j] <= plants[i]) {
                    if (lis[j].size + 1 > lis[i].size
                            || ((lis[j].size + 1 == lis[i].size) && lis[j].sum + plants[i] > lis[i].sum)) {
                        lis[i].sum = lis[j].sum + plants[i];
                        lis[i].size = lis[i].size + 1;
                        lis[i].prev = j;
                    }
                }
            }
        }

        return lis;
    }
}
