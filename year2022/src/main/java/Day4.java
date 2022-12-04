import util.Pair;

import java.util.stream.Stream;

public class Day4 extends Day {

    record Range(int a, int b) {

        public boolean contains(Range range) {
            return a <= range.a && b >= range.b;
        }

        public boolean overlaps(Range range) {
            return a <= range.b && b >= range.a;
        }

    }

    private static Range range(String range) {
        String[] s = range.split("\\-");
        return new Range(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    @Override
    protected long resolveP1(Stream<String> input) {
        return input.map(line -> line.split(","))
                .map(ranges -> new Pair<>(range(ranges[0]), range(ranges[1])))
                .filter(ranges -> ranges.a().contains(ranges.b()) || ranges.b().contains(ranges.a()))
                .count();
    }

    @Override
    protected long resolveP2(Stream<String> input) {
        return input.map(line -> line.split(","))
                .map(ranges -> new Pair<>(range(ranges[0]), range(ranges[1])))
                .filter(ranges -> ranges.a().overlaps(ranges.b()))
                .count();
    }


    public static void main(String[] args) {
        new Day4().resolve();
    }
}
