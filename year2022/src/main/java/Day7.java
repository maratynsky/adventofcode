import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 extends Day<Integer> {

    interface Node {
        String name();
        long size();
    }

    record File(String name, long size) implements Node {
    }

    static class Dir implements Node {
        final String name;
        long size = -1;

        final Map<String, Node> nodes = new HashMap<>();

        public Dir(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public long size() {
            return size;
        }
    }

    static class FileSystem {
        Dir root;
        Dir workingDir;

        void execute(String cmd) {

        }
    }

    @Override
    protected Integer resolveP1(Stream<String> input) {

        FileSystem fs = new FileSystem();

        for (String line : input.collect(Collectors.toList())) {

        }
    }

    private static boolean isCommand(String line) {

    }

    @Override
    protected Integer resolveP2(Stream<String> input) {
        return 0;
    }


    public static void main(String[] args) {
        new Day7().resolve();
    }

}
