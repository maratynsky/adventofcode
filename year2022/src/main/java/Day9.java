import util.Pair;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.signum;
import static java.lang.Math.abs;

public class Day9 extends Day<Integer> {

    static class Knot {
        int x;
        int y;

        public Knot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Knot knot = (Knot) o;
            return x == knot.x && y == knot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static class Rope {
        Knot[] rope;

        public Rope(int length) {
            rope = new Knot[length];
            for (int i = 0; i < rope.length; i++) {
                rope[i] = new Knot(0, 0);
            }
        }

        Rope move(char direction) {
            switch (direction) {
                case 'U' -> rope[0].y++;
                case 'D' -> rope[0].y--;
                case 'L' -> rope[0].x--;
                case 'R' -> rope[0].x++;
            }
            for (int i = 1; i < rope.length; i++) {
                moveNext(rope[i - 1], rope[i]);
            }
            return this;
        }

        public Knot tail() {
            Knot tail = rope[rope.length - 1];
            return new Knot(tail.x, tail.y);
        }

        private void moveNext(Knot previous, Knot next) {
            if (abs(previous.y - next.y) > 1 && abs(previous.x - next.x) > 1) {
                next.y += signum(previous.y - next.y);
                next.x += signum(previous.x - next.x);
            } else if (abs(previous.y - next.y) > 1) {
                next.y += signum(previous.y - next.y);
                next.x = previous.x;
            } else if (abs(previous.x - next.x) > 1) {
                next.x += signum(previous.x - next.x);
                next.y = previous.y;
            }
        }

        public void testPrint(int n) {
            char[][] map = new char[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    map[i][j] = '.';
                }
            }

            for (int i = 0; i < rope.length; i++) {
                if (i == 0) {
                    map[n - 1 - rope[i].y][rope[i].x] = 'H';
                } else {
                    map[n - 1 - rope[i].y][rope[i].x] = ("" + i).charAt(0);
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
            System.out.println("---------------------------");

        }

    }

    @Override
    protected Integer resolveP1(Stream<String> input) {
        return resolve(2, input);
    }

    @Override
    protected Integer resolveP2(Stream<String> input) {
        return resolve(10, input);
    }

    private int resolve(int length, Stream<String> input) {
        Rope rope = new Rope(length);
        Collection<Knot> visited = input.flatMap(line -> {
            char direction = line.charAt(0);
            int count = Integer.parseInt(line.substring(2));

            return IntStream.range(0, count)
                    .mapToObj(i -> rope.move(direction).tail());
        }).collect(Collectors.toSet());


        return visited.size();
    }

    private void test() {
        Rope rope = new Rope(10);
        for (int i = 0; i < 4; i++) {
            rope.move('R');
            rope.testPrint(6);
        }

        for (int i = 0; i < 4; i++) {
            rope.move('U');
            rope.testPrint(6);
        }


    }

    public static void main(String[] args) {
//        new Day9().test();
        new Day9().resolve();
    }

}
