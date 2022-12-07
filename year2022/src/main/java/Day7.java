import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 extends Day<Long> {

    interface Node {
        String name();

        long size();
    }

    record File(String name, long size) implements Node {
    }

    static class Dir implements Node {
        final Dir parent;
        final String name;
        long size = -1;

        final Map<String, Node> nodes = new HashMap<>();

        public Dir(Dir parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public long size() {
            if (size != -1) {
                return size;
            } else {
                return size = nodes.values().stream().mapToLong(Node::size).sum();
            }
        }

        public Dir getDir(String subDir) {
            return subDir.equals("..")
                    ? parent
                    : ((Dir) nodes.get(subDir));
        }

        public void addDir(String subDir) {
            nodes.put(subDir, new Dir(this, subDir));
        }

        public void addFile(String fineName, long size) {
            nodes.put(fineName, new File(fineName, size));
        }
    }

    static class FileSystem {
        Dir root;
        Dir workingDir;

        void feed(String line) {
            if (isCommand(line)) {
                execute(line);
            } else {
                if (isDir(line)) {
                    workingDir.addDir(line.substring(4));
                } else {
                    String[] parts = line.split(" ");
                    workingDir.addFile(parts[1], Long.parseLong(parts[0]));
                }
            }
        }

        boolean isDir(String line) {
            return line.startsWith("dir");
        }

        boolean isCommand(String line) {
            return line.startsWith("$");
        }

        void execute(String cmd) {
            if (cmd.startsWith("$ cd")) {
                String subDir = cmd.substring(5);
                if (root == null) {
                    root = new Dir(null, "/");
                    workingDir = root;
                } else {
                    workingDir = workingDir.getDir(subDir);
                }
            }
        }

        public long p1() {
            return p1(root);
        }

        public long p1(Dir dir) {
            long selfSize = dir.size() <= 100000
                    ? dir.size()
                    : 0;
            return selfSize + dir.nodes.values().stream()
                    .filter(Dir.class::isInstance)
                    .map(Dir.class::cast)
                    .mapToLong(this::p1)
                    .sum();
        }

        public long size() {
            return root.size();
        }

        public long p2(long toDelete) {
            return allDirs(root)
                    .mapToLong(Dir::size)
                    .filter(size -> size > toDelete)
                    .sorted()
                    .findFirst()
                    .orElseThrow();
        }

        public Stream<Dir> allDirs(Dir dir) {
            return Stream.concat(Stream.of(dir),
                    dir.nodes.values().stream()
                            .filter(Dir.class::isInstance)
                            .map(Dir.class::cast)
                            .flatMap(this::allDirs));
        }
    }

    @Override
    protected Long resolveP1(Stream<String> input) {
        FileSystem fs = new FileSystem();
        input.forEach(fs::feed);
        return fs.p1();
    }

    @Override
    protected Long resolveP2(Stream<String> input) {
        FileSystem fs = new FileSystem();
        input.forEach(fs::feed);
        long used = fs.size();
        long total = 70000000;
        long required = 30000000;
        long free = total - used;
        long toDelete = required - free;
        return fs.p2(toDelete);
    }

    public static void main(String[] args) {
        new Day7().resolve();
    }

}
