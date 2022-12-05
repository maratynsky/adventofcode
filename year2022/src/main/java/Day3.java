import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static util.Util.batches;

public class Day3 extends Day<Long> {

    private static int weight(int c) {
        return (c >= 'a' && c <= 'z')
                ? c - 'a' + 1
                : c - 'A' + 27;
    }

    @Override
    protected Long resolveP1(Stream<String> input) {
        return (long) input.mapToInt(rucksack -> {
            int itemCount = rucksack.length();

            Set<Integer> compartment1 = rucksack.chars()
                    .limit(itemCount / 2)
                    .mapToObj(Day3::weight)
                    .collect(Collectors.toSet());

            Set<Integer> compartment2 = rucksack.chars()
                    .skip(itemCount / 2)
                    .mapToObj(Day3::weight)
                    .collect(Collectors.toSet());

            Set<Integer> intersection = new HashSet<>(compartment1);
            intersection.retainAll(compartment2);
            return intersection.iterator().next();
        }).sum();
    }

    @Override
    protected Long resolveP2(Stream<String> input) {
        return (long) batches(input, 3)
                .mapToInt(group -> {
                    Set<Integer> elf1 = group.get(0).chars()
                            .mapToObj(Day3::weight)
                            .collect(Collectors.toSet());

                    Set<Integer> elf2 = group.get(1).chars()
                            .mapToObj(Day3::weight)
                            .collect(Collectors.toSet());

                    Set<Integer> elf3 = group.get(2).chars()
                            .mapToObj(Day3::weight)
                            .collect(Collectors.toSet());

                    Set<Integer> intersection = new HashSet<>(elf1);
                    intersection.retainAll(elf2);
                    intersection.retainAll(elf3);
                    return intersection.iterator().next();

                }).sum();
    }


    public static void main(String[] args) {
        new Day3().resolve();
    }
}
