import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 extends Day<Long> {

    record Monkeys(Monkey[] monkeys, int magicNumber) {

        void round() {
            for (Monkey monkey : monkeys) {
                long[][] inspectionResults = monkey.inspect();
                for (long[] inspectionResult : inspectionResults) {
                    long worryLevel = inspectionResult[0];
                    int monkeyThrowTo = (int) inspectionResult[1];
                    monkeys[monkeyThrowTo].throwItem(worryLevel, magicNumber);
                }
            }
        }

        long monkeyBusinessLevel() {
            return Arrays.stream(monkeys).map(value -> value.inspected)
                    .map(Long::valueOf)
                    .sorted(Comparator.reverseOrder())
                    .limit(2)
                    .reduce(1L, (integer, integer2) -> integer * integer2);
        }

        void print() {
            for (int i = 0; i < monkeys.length; i++) {
                System.out.println("Monkey " + i + ": " + monkeys[i].items.stream().map(Objects::toString).collect(Collectors.joining(", ")));
            }
        }
    }

    static final class Monkey {
        private final Queue<Long> items;
        private final Function<Long, Long> operation;
        private final Predicate<Long> test;
        private final Function<Boolean, Integer> throwToMonkeyIf;

        private final int divideWorryLevelBy;

        int inspected = 0;

        Monkey(Collection<Long> items,
               Function<Long, Long> operation,
               Predicate<Long> test,
               Function<Boolean, Integer> throwToMonkeyIf, int divideWorryLevelBy) {
            this.items = new LinkedList<>(items);
            this.operation = operation;
            this.test = test;
            this.throwToMonkeyIf = throwToMonkeyIf;
            this.divideWorryLevelBy = divideWorryLevelBy;
        }

        // worry level, monkey to throw
        long[][] inspect() {
            inspected += items.size();
            return Stream.generate(items::poll)
                    .takeWhile(Objects::nonNull)
                    .map(worryLevel -> {
                        worryLevel = operation.apply(worryLevel) / divideWorryLevelBy;
                        return new long[]{worryLevel, throwToMonkeyIf.apply(test.test(worryLevel))};
                    }).toArray(long[][]::new);
        }

        void throwItem(long item, int magicNumber) {
            items.add(item % magicNumber);
        }
    }

    @Override
    protected Long resolveP1(Stream<String> input) {
        Monkeys monkeys = monkeys(input, 3);
        monkeys.print();
        for (int i = 0; i < 20; i++) {
            monkeys.round();
            System.out.println("After round " + (i + 1));
            monkeys.print();
        }

        return monkeys.monkeyBusinessLevel();
    }

    private Monkeys monkeys(Stream<String> input, int divideWorryLevelBy) {
        String notes = input.collect(Collectors.joining("\n"));

        String[] monkeyNotes = notes.split("\n\n");

        Monkey[] monkeys = new Monkey[monkeyNotes.length];

        int magicNumber = 1;

        for (int i = 0; i < monkeyNotes.length; i++) {
            String monkeyNote = monkeyNotes[i];
            String[] s = monkeyNote.split("\n");
            List<Long> startingItems = Arrays.stream(s[1].substring(18).split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .toList();

            String s2 = s[2].substring(23);
            char op = s2.charAt(0);
            String operandStr = s2.substring(2);


            Function<Long, Long> operation;

            if (operandStr.equals("old")) {
                operation = switch (op) {
                    case '*' -> x -> x * x;
                    case '+' -> x -> x + x;
                    default -> throw new RuntimeException();
                };
            } else {
                int operand = Integer.parseInt(operandStr);
                operation = switch (op) {
                    case '*' -> x -> x * operand;
                    case '+' -> x -> x + operand;
                    default -> throw new RuntimeException();
                };
            }


            int s3 = Integer.parseInt(s[3].substring(21));
            Predicate<Long> test = x -> x % s3 == 0;
            magicNumber *= s3;

            int s4 = Integer.parseInt(s[4].substring(29));
            int s5 = Integer.parseInt(s[5].substring(30));

            Function<Boolean, Integer> throwToMonkeyIf = result -> result ? s4 : s5;

            monkeys[i] = new Monkey(startingItems, operation, test, throwToMonkeyIf, divideWorryLevelBy);
        }

        return new Monkeys(monkeys, magicNumber);
    }

    @Override
    protected Long resolveP2(Stream<String> input) {
        Monkeys monkeys = monkeys(input, 1);
        for (int i = 0; i < 10000; i++) {
            monkeys.round();
            if ((i + 1) <= 20 || (i + 1)     % 1000 == 0) {
                System.out.println("After round " + (i + 1));
                monkeys.print();
            }
        }

        return monkeys.monkeyBusinessLevel();
    }


    public static void main(String[] args) {
        new Day11().resolve();
    }

}
