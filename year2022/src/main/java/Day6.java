import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 extends Day<Integer[]> {

    static class Ring {
        final char[] ring;
        int next;

        public Ring(int count) {
            this.ring = new char[count];
            this.next = 0;
        }

        public boolean addAndCheckMarker(char c) {
            ring[next++ % ring.length] = c;
            return isMarker();
        }

        public boolean isMarker() {
            for (int i = 0; i < ring.length; i++) {
                for (int j = i + 1; j < ring.length; j++) {
                    if (ring[i] == ring[j]) {
                        return false;
                    }
                }
            }
            return true;
        }

    }

    @Override
    protected Integer[] resolveP1(Stream<String> input) {
        return resolve(input, 4);
    }

    @Override
    protected Integer[] resolveP2(Stream<String> input) {
        return resolve(input, 14);
    }

    private static Integer[] resolve(Stream<String> input, int count) {
        return input.map(String::toCharArray)
                .map(signal -> {
                    Ring r = new Ring(count);
                    for (int i = 0; i < signal.length; i++) {
                        if (r.addAndCheckMarker(signal[i]) && i >= 3) {
                            return i + 1;
                        }
                    }
                    return -1;
                }).toArray(Integer[]::new);
    }

    public static void main(String[] args) {
        new Day6().resolve();
    }

    @Override
    protected String toString(Integer[] result) {
        return Arrays.stream(result).map(String::valueOf).collect(Collectors.joining(", "));
    }
}
