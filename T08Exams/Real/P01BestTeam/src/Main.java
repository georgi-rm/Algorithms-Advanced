import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Integer> soldiersRating = Arrays.stream(scanner.nextLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> soldiersInIncreasingOrder = getLIS(soldiersRating, true);
        Collections.reverse(soldiersInIncreasingOrder);

        Collections.reverse(soldiersRating);

        List<Integer> soldiersInDecreasingOrder = getLIS(soldiersRating, false);

        List<Integer> result;
        if (soldiersInIncreasingOrder.size() > soldiersInDecreasingOrder.size()) {
            result = soldiersInIncreasingOrder;
        } else {
            result = soldiersInDecreasingOrder;
        }

        for (Integer soldierRating : result) {
            System.out.printf("%d ", soldierRating);
        }
    }

    private static List<Integer> getLIS(List<Integer> soldiersRating, boolean isLeftMostOne) {
        int[] lengths = new int[soldiersRating.size()];
        int[] previous = new int[soldiersRating.size()];

        int maxLength = 0;
        int maxLengthIndex = 0;

        Arrays.fill(previous, -1);

        for (int i = 1; i <= soldiersRating.size() - 1; i++) {
            for (int j = 0; j < i; j++) {
                if (soldiersRating.get(j) <= soldiersRating.get(i)) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[i] + 1;
                        if (lengths[i] > maxLength ||
                                (lengths[i] == maxLength && !isLeftMostOne)) {
                            maxLength = lengths[i];
                            maxLengthIndex = i;
                        }
                        previous[i] = j;
                    }
                }
            }
        }

        List<Integer> soldiers = new ArrayList<>();

        int index = maxLengthIndex;

        while (index >= 0) {
            soldiers.add(soldiersRating.get(index));
            index = previous[index];
        }

        return soldiers;
    }
}
