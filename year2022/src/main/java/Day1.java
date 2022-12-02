import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day1 extends Day {

    @Override
    protected long resolveP1(Stream<String> input) {
        List<String> calories = input.toList();

        long maxCalories = 0;
        long totalCaloriesCarriedByCurrentElf = 0;

        for (String c : calories) {
            if (c.isBlank()) {
                if (totalCaloriesCarriedByCurrentElf > maxCalories) {
                    maxCalories = totalCaloriesCarriedByCurrentElf;
                }
                totalCaloriesCarriedByCurrentElf = 0;
                continue;
            }
            totalCaloriesCarriedByCurrentElf += Long.parseLong(c);
        }

        if (totalCaloriesCarriedByCurrentElf > 0) {
            if (totalCaloriesCarriedByCurrentElf > maxCalories) {
                maxCalories = totalCaloriesCarriedByCurrentElf;
            }
        }

        return maxCalories;
    }

    @Override
    protected long resolveP2(Stream<String> input) {
        List<String> calories = input.toList();

        List<Long> totalCaloriesCarriedByElfs = new ArrayList<>();
        long totalCaloriesCarriedByCurrentElf = 0;

        for (String c : calories) {
            if (c.isBlank()) {
                totalCaloriesCarriedByElfs.add(totalCaloriesCarriedByCurrentElf);
                totalCaloriesCarriedByCurrentElf = 0;
                continue;
            }
            totalCaloriesCarriedByCurrentElf += Long.parseLong(c);
        }

        if (totalCaloriesCarriedByCurrentElf > 0) {
            totalCaloriesCarriedByElfs.add(totalCaloriesCarriedByCurrentElf);
        }

        return totalCaloriesCarriedByElfs.stream().sorted(Comparator.reverseOrder()).limit(3)
                .mapToLong(Long::longValue).sum();
    }


    public static void main(String[] args) {
        System.out.println(new Day1().resolveP1());
        System.out.println(new Day1().resolveP2());
    }
}
