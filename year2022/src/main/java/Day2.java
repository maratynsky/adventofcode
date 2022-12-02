import java.util.stream.Stream;

public class Day2 extends Day {

    private final static int WIN = 6;
    private final static int DRAW = 3;
    private final static int LOSE = 0;

    enum Shape {
        ROCK(1), PAPER(2), SCISSORS(3);

        private final int score;

        Shape(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public int result(Shape opponentsShape) {
            if (this == opponentsShape) {
                return DRAW;
            }
            final boolean win = this == ROCK && opponentsShape == SCISSORS
                    || this == PAPER && opponentsShape == ROCK
                    || this == SCISSORS && opponentsShape == PAPER;
            return win ? WIN : LOSE;
        }

        public int getGameScoreWith(Shape opponentsShape) {
            return score + result(opponentsShape);
        }

        public Shape getWinningShape() {
            return switch (this) {
                case ROCK -> PAPER;
                case PAPER -> SCISSORS;
                case SCISSORS -> ROCK;
            };
        }

        public Shape getLosingShape() {
            return switch (this) {
                case ROCK -> SCISSORS;
                case PAPER -> ROCK;
                case SCISSORS -> PAPER;
            };
        }

        static Shape get(char c) {
            return switch (c) {
                case 'A', 'X' -> ROCK;
                case 'B', 'Y' -> PAPER;
                case 'C', 'Z' -> SCISSORS;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }
    }

    record Game(Shape myShape, Shape opponentsShape) {
        static Game fromP1(String in) {
            return new Game(Shape.get(in.charAt(2)), Shape.get(in.charAt(0)));
        }

        static Game fromP2(String in) {
            final Shape opponentsShape = Shape.get(in.charAt(0));
            return new Game(choose(opponentsShape, in.charAt(2)), opponentsShape);
        }

        private static Shape choose(Shape opponentsShape, char result) {
            return switch (result) {
                case 'X' -> opponentsShape.getLosingShape();
                case 'Y' -> opponentsShape;
                case 'Z' -> opponentsShape.getWinningShape();
                default -> throw new IllegalStateException("Unexpected value: " + result);
            };
        }

        public int score() {
            return myShape.getGameScoreWith(opponentsShape);
        }
    }

    @Override
    protected long resolveP1(Stream<String> input) {
        return input.map(Game::fromP1).mapToInt(Game::score).sum();

    }

    @Override
    protected long resolveP2(Stream<String> input) {
        return input.map(Game::fromP2).mapToInt(Game::score).sum();
    }


    public static void main(String[] args) {
        System.out.println(new Day2().resolveP1());
        System.out.println(new Day2().resolveP2());
    }
}
