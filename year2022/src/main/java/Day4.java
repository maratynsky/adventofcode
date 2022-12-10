import util.Pair;

import java.util.stream.Stream;

public class Day4 extends Day<Long> {

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
    protected Long resolveP1(Stream<String> input) {
        return input.map(line -> line.split(","))
                .map(ranges -> new Pair<>(range(ranges[0]), range(ranges[1])))
                .filter(ranges -> ranges.x().contains(ranges.y()) || ranges.y().contains(ranges.x()))
                .count();
    }

    @Override
    protected Long resolveP2(Stream<String> input) {
        return input.map(line -> line.split(","))
                .map(ranges -> new Pair<>(range(ranges[0]), range(ranges[1])))
                .filter(ranges -> ranges.x().overlaps(ranges.y()))
                .count();
    }


    public static void main(String[] args) {
        new Day4().resolve();
    }
}
