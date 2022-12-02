import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class Day {

    protected abstract long resolveP1(Stream<String> input);
    protected abstract long resolveP2(Stream<String> input);

    public long resolveP1() {
        try {
            final var className = this.getClass().getSimpleName();
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(className + ".in")).toURI());
            return resolveP1(Files.lines(path));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public long resolveP2() {
        try {
            final var className = this.getClass().getSimpleName();
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(className + ".in")).toURI());
            return resolveP2(Files.lines(path));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
