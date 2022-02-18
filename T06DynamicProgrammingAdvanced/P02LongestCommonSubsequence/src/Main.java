import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String firstText = scanner.nextLine();
        String secondText = scanner.nextLine();

        int[][] dp = new int[firstText.length() + 1][secondText.length() + 1];

        for (int first = 1; first <= firstText.length(); first++) {
            for (int second = 1; second <= secondText.length(); second++) {
                if (firstText.charAt(first - 1) == secondText.charAt(second - 1)) {
                    dp[first][second] = dp[first - 1][second - 1] + 1;
                } else {
                    dp[first][second] = Math.max(dp[first - 1][second], dp[first][second - 1]);
                }
            }
        }
        System.out.println(dp[firstText.length()][secondText.length()]);
    }
}
