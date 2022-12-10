import java.util.stream.Stream;

public class Day10 extends Day<Integer> {

    static class CPU {
        int X = 1;
        int cycle = 1;
        int counter = 0;

        StringBuilder crt = new StringBuilder();

        public void execute(String cmd) {
            if (cmd.startsWith("addx")) {
                int v = Integer.parseInt(cmd.substring(5));
                incCycle();
                incCycle();
                X += v;
            } else if (cmd.startsWith("noop")) {
                incCycle();
            }
        }

        private void incCycle() {
            if ((cycle + 20) % 40 == 0) {
                counter += cycle * X;
            }
            drawPixel();
            cycle++;
        }

        private void drawPixel() {
            if ((cycle - 1) % 40 >= X - 1 && (cycle - 1) % 40 <= X + 1) {
                crt.append("#");
            } else {
                crt.append(".");
            }
            if (cycle % 40 == 0) {
                crt.append("\n");
            }
        }

        private String display() {
            return crt.toString();
        }
    }

    @Override
    protected Integer resolveP1(Stream<String> input) {
        CPU cpu = new CPU();
        input.forEach(cpu::execute);
        return cpu.counter;
    }

    @Override
    protected Integer resolveP2(Stream<String> input) {
        CPU cpu = new CPU();
        input.forEach(cpu::execute);
        System.out.println(cpu.display());
        return 0;
    }


    public static void main(String[] args) {
        new Day10().resolve();
    }

}
