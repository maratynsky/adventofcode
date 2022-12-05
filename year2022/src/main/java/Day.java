import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class Day<R> {

    protected abstract R resolveP1(Stream<String> input);

    protected abstract R resolveP2(Stream<String> input);

    public R resolveP1() {
        try {
            final var className = this.getClass().getSimpleName();
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(className + ".in")).toURI());
            return resolveP1(Files.lines(path));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public R resolveP2() {
        try {
            final var className = this.getClass().getSimpleName();
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(className + ".in")).toURI());
            return resolveP2(Files.lines(path));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void resolve() {
        System.out.println("Part 1 = " + resolveP1());
        System.out.println("Part 2 = " + resolveP2());
    }

}
