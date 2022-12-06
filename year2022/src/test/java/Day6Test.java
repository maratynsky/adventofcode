import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Day6Test {

    @Test
    void testP1() {
        assertArrayEquals(new Integer[]{7, 5, 6, 10, 11}, new Day6().resolveP1());
    }

    @Test
    void testP2() {
        assertArrayEquals(new Integer[]{19, 23, 23, 29, 26}, new Day6().resolveP2());
    }
}
