import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String firstText = scanner.nextLine();
        String secondText = scanner.nextLine();

        int[][] dp = new int[firstText.length() + 1][secondText.length() + 1];
        int maxLength = 0;
        int maxRow = 0;
        int maxColumn = 0;

        for (int row = 1; row <= firstText.length(); row++) {
            for (int column = 1; column <= secondText.length(); column++) {
                if (firstText.charAt(row - 1) == secondText.charAt(column - 1)) {
                    dp[row][column] = dp[row - 1][column - 1] + 1;
                    if (dp[row][column] > maxLength) {
                        maxLength = dp[row][column];
                        maxRow = row;
                        maxColumn = column;
                    }
                }
            }
        }

        List<Character> result = new ArrayList<>();

        while (maxRow > 0 && maxColumn > 0 && dp[maxRow][maxColumn] > 0) {
            maxRow--;
            maxColumn--;
            result.add(firstText.charAt(maxRow));
        }

        Collections.reverse(result);
        for (Character character : result) {
            System.out.printf("%c", character);
        }
    }
}
