import util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Day14 extends Day<Integer> {

    static class Cave {
        final static int AIR = 0;
        final static int ROCK = 1;
        final static int SAND = 2;
        final int[][] map;

        static final int startPositionX = 500;
        static final int startPositionY = 0;

        final boolean withFloor;

        Cave(int[][] map, boolean withFloor) {
            this.map = map;
            this.withFloor = withFloor;
        }


        Optional<Pair<Integer, Integer>> produceSand() {
            boolean resting = false;

            int x = startPositionX;
            int y = startPositionY;

            while (!resting) {

                if (withFloor && y == map.length - 2) {
                    map[y][x] = SAND;
                    resting = true;
                }

                if (y >= map.length - 1) break;

                if (map[y + 1][x] == AIR) {
                    y++;
                } else if (map[y + 1][x - 1] == AIR) {
                    x--;
                    y++;
                } else if (map[y + 1][x + 1] == AIR) {
                    x++;
                    y++;
                } else {
                    map[y][x] = SAND;
                    resting = true;
                }
            }

            return resting ? Optional.of(new Pair<>(x, y)) : Optional.empty();
        }


        void print() {
            for (int[] row : map) {
                for (int cell : row) {
                    System.out.print(cell(cell));
                }
                System.out.println();
            }
        }

        char cell(int value) {
            return switch (value) {
                case ROCK -> '#';
                case AIR -> '.';
                case SAND -> 'O';
                default -> throw new IllegalStateException("Unexpected value: " + value);
            };
        }


        static Cave parse(Stream<String> input, boolean withFloor) {
            List<List<Pair<Integer, Integer>>> paths = input.map(line ->
                    Arrays.stream(line.split(" -> "))
                            .map(pair -> pair.split(","))
                            .map(pair -> new Pair<>(parseInt(pair[0]), parseInt(pair[1])))
                            .collect(Collectors.toList())
            ).toList();

            int xMax = 0;
            int yMax = 0;

            for (List<Pair<Integer, Integer>> path : paths) {
                for (Pair<Integer, Integer> point : path) {
                    if (point.x() > xMax) {
                        xMax = point.x();
                    }
                    if (point.y() > yMax) {
                        yMax = point.y();
                    }
                }
            }

            int xx = withFloor
                    ? Cave.startPositionX + yMax + 10
                    : xMax + 2;
            int[][] caveMap = new int[yMax + 2 + (withFloor ? 1 : 0)][xx];

            for (List<Pair<Integer, Integer>> path : paths) {
                for (int i = 1; i < path.size(); i++) {
                    var start = path.get(i - 1);
                    var end = path.get(i);

                    var xStart = Math.min(start.x(), end.x());
                    var yStart = Math.min(start.y(), end.y());

                    var xEnd = Math.max(start.x(), end.x());
                    var yEnd = Math.max(start.y(), end.y());

                    for (int y = yStart; y <= yEnd; y++) {
                        for (int x = xStart; x <= xEnd; x++) {
                            caveMap[y][x] = ROCK;
                        }
                    }

                }
            }

            return new Cave(caveMap, withFloor);
        }

    }


    @Override
    protected Integer resolveP1(Stream<String> input) {
        Cave cave = Cave.parse(input, false);
        int c = 0;
        while (cave.produceSand().isPresent()) {
            c++;
        }
        return c;
    }

    @Override
    protected Integer resolveP2(Stream<String> input) {
        Cave cave = Cave.parse(input, true);
        int c = 0;
        while (true) {
            Optional<Pair<Integer, Integer>> rest = cave.produceSand();

            if (rest.isEmpty()) break;
            Pair<Integer, Integer> restPoint = rest.get();
            if (restPoint.x() == Cave.startPositionX && restPoint.y() == Cave.startPositionY) break;
            c++;
        }
        return c+1;
    }
    public static void main(String[] args) {
        new Day14().resolve();
    }

}
