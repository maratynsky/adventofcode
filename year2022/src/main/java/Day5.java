import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 extends Day<String> {

    record Move(int count, int from, int to) {
        //move 1 from 3 to 5

        static Move parse(String in) {
            final String[] s = in.split(" ");
            return new Move(Integer.parseInt(s[1]), Integer.parseInt(s[3]), Integer.parseInt(s[5]));
        }
    }

    static class Ship {
        private final List<Stack<Character>> stacks;

        void rearrange(Move move) {
            final Stack<Character> from = stacks.get(move.from() - 1);
            final Stack<Character> to = stacks.get(move.to() - 1);
            for (int i = 0; i < move.count(); i++) {
                to.push(from.pop());
            }
        }

        void rearrangeCrateMover9001(Move move) {
            final Stack<Character> from = stacks.get(move.from() - 1);
            final Stack<Character> to = stacks.get(move.to() - 1);
            final Stack<Character> tmp = new Stack<>();
            for (int i = 0; i < move.count(); i++) {
                tmp.push(from.pop());
            }
            for (int i = 0; i < move.count(); i++) {
                to.push(tmp.pop());
            }

        }

        String getTopCrates() {
            return stacks.stream().map(Stack::peek).map(Object::toString).collect(Collectors.joining());
        }

        Ship(String[] lines) {
            final int stacksCount = Arrays.stream(lines[lines.length - 1].split("\s+"))
                    .filter(s -> !s.isBlank())
                    .mapToInt(Integer::parseInt).max().orElseThrow();


            this.stacks = new ArrayList<>(stacksCount);

            for (int i = 0; i < stacksCount; i++) {
                stacks.add(new Stack<>());
            }

            for (int i = lines.length - 2; i >= 0; i--) {
                String line = lines[i];
                for (int stack = 0; stack < stacksCount; stack++) {
                    String crate = line.substring(stack * 4, stack * 4 + 3);
                    if (!crate.isBlank()) {
                        Character crateLetter = crate.charAt(1);
                        stacks.get(stack).push(crateLetter);
                    }
                }
            }
        }

        @Override
        public String toString() {
            final List<String> lines = Stream.iterate(0, i -> i + 1)
                    .map(line -> stacks.stream().map(stack -> {
                        if (stack.size() >= line + 1) {
                            final Character crate = stack.get(line);
                            return "[" + crate + "]";
                        } else {
                            return "   ";
                        }
                    }).collect(Collectors.joining(" ")))
                    .takeWhile(s -> !s.isBlank())
                    .toList();

            StringBuilder sb = new StringBuilder();

            for (int i = lines.size() - 1; i >= 0; i--) {
                sb.append(lines.get(i)).append("\n");
            }

            sb.append(IntStream.range(1, stacks.size() + 1).mapToObj(stack -> " " + stack + " ").collect(Collectors.joining(" ")))
                    .append("\n");


            return sb.toString();
        }
    }

    @Override
    protected String resolveP1(Stream<String> input) {
        return resolve(input, Ship::rearrange);
    }

    @Override
    protected String resolveP2(Stream<String> input) {
        return resolve(input, Ship::rearrangeCrateMover9001);
    }

    protected String resolve(Stream<String> input, BiConsumer<Ship, Move> rearrange) {
        final String in = input.collect(Collectors.joining("\n"));
        final String[] s = in.split("\n\n");

        final String[] lines = s[0].split("\n");
        final String[] moves = s[1].split("\n");

        Ship ship = new Ship(lines);
        System.out.println("Initial state:");
        System.out.println(ship);
        System.out.println("----------------------------------");

        for (String moveStr : moves) {
            Move move = Move.parse(moveStr);
            rearrange.accept(ship, move);
            System.out.println("Rearrange: " + moveStr);
            System.out.println(ship);
            System.out.println("----------------------------------");
        }

        return ship.getTopCrates();
    }

    public static void main(String[] args) {
        new Day5().resolve();
    }
}
