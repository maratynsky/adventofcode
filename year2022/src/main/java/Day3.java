import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Day3 extends Day {

    private static int weight(int c) {
        return (c >= 'a' && c <= 'z')
                ? c - 'a' + 1
                : c - 'A' + 27;
    }

    @Override
    protected long resolveP1(Stream<String> input) {
        return input.mapToInt(rucksack -> {
            int itemCount = rucksack.length();

            Set<Integer> compartment1 = rucksack.chars()
                    .limit(itemCount / 2)
                    .mapToObj(Day3::weight)
                    .collect(Collectors.toSet());

            Set<Integer> compartment2 = rucksack.chars()
                    .skip(itemCount / 2)
                    .mapToObj(Day3::weight)
                    .collect(Collectors.toSet());

            Set<Integer> intersection = new HashSet<>(compartment1);
            intersection.retainAll(compartment2);
            return intersection.iterator().next();
        }).sum();
    }

    @Override
    protected long resolveP2(Stream<String> input) {
        return batches(input, 3)
                .mapToInt(group -> {
                    Set<Integer> elf1 = group.get(0).chars()
                            .mapToObj(Day3::weight)
                            .collect(Collectors.toSet());

                    Set<Integer> elf2 = group.get(1).chars()
                            .mapToObj(Day3::weight)
                            .collect(Collectors.toSet());

                    Set<Integer> elf3 = group.get(2).chars()
                            .mapToObj(Day3::weight)
                            .collect(Collectors.toSet());

                    Set<Integer> intersection = new HashSet<>(elf1);
                    intersection.retainAll(elf2);
                    intersection.retainAll(elf3);
                    return intersection.iterator().next();

                }).sum();
    }


    public static void main(String[] args) {
        new Day3().resolve();
    }


    public static <T> Stream<List<T>> batches(Stream<T> stream, int batchSize) {
        return batchSize <= 0
                ? Stream.of(stream.collect(Collectors.toList()))
                : StreamSupport.stream(new BatchSpliterator<>(stream.spliterator(), batchSize), stream.isParallel());
    }

    private static class BatchSpliterator<E> implements Spliterator<List<E>> {

        private final Spliterator<E> base;
        private final int batchSize;

        public BatchSpliterator(Spliterator<E> base, int batchSize) {
            this.base = base;
            this.batchSize = batchSize;
        }

        @Override
        public boolean tryAdvance(Consumer<? super List<E>> action) {
            final List<E> batch = new ArrayList<>(batchSize);
            for (int i = 0; i < batchSize && base.tryAdvance(batch::add); i++) ;
            if (batch.isEmpty())
                return false;
            action.accept(batch);
            return true;
        }

        @Override
        public Spliterator<List<E>> trySplit() {
            if (base.estimateSize() <= batchSize)
                return null;
            final Spliterator<E> splitBase = this.base.trySplit();
            return splitBase == null ? null
                    : new BatchSpliterator<>(splitBase, batchSize);
        }

        @Override
        public long estimateSize() {
            final double baseSize = base.estimateSize();
            return baseSize == 0 ? 0
                    : (long) Math.ceil(baseSize / (double) batchSize);
        }

        @Override
        public int characteristics() {
            return base.characteristics();
        }

    }
}
