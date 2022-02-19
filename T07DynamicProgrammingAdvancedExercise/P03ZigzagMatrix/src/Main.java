import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfRows = Integer.parseInt(scanner.nextLine());
        int numberOfColumns = Integer.parseInt(scanner.nextLine());

        int[][] matrix = new int[numberOfRows][];

        for (int row = 0; row < numberOfRows; row++) {
            matrix[row] = Arrays.stream(scanner.nextLine().split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        int[][] dp = new int[numberOfRows][numberOfColumns];
        int[][] parents = new int[numberOfRows][numberOfColumns];

        for (int row = 0; row < numberOfRows; row++) {
            dp[row][0] = matrix[row][0];
        }

        for (int column = 1; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {
                int previousMax = 0;

                if (column % 2 != 0) {
                    for (int i = row + 1; i < numberOfRows; i++) {
                        if (dp[i][column - 1] > previousMax) {
                            previousMax = dp[i][column - 1];
                            parents[row][column] = i;
                        }
                    }
                } else {
                    for (int i = 0; i < row; i++) {
                        if (dp[i][column - 1] > previousMax) {
                            previousMax = dp[i][column - 1];
                            parents[row][column] = i;
                        }
                    }
                }
                dp[row][column] = previousMax + matrix[row][column];
            }
        }

        List<Integer> result = new ArrayList<>();

        int max = -1;
        int rowIndex = 0;

        for (int row = 0; row < dp.length; row++) {
            if (dp[row][numberOfColumns - 1] > max) {
                rowIndex = row;
                max = dp[row][numberOfColumns - 1];
            }
        }

        for (int column = numberOfColumns - 1; column >= 0; column--) {
            result.add(matrix[rowIndex][column]);
            rowIndex = parents[rowIndex][column];
        }

        Collections.reverse(result);

        System.out.printf("%d = %s", result.stream()
                        .mapToInt(e -> e)
                        .sum(),
                result.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" + ")));
    }
}
