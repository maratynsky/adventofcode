import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

    @Test
    void testP1() {
        assertEquals("CMZ", new Day5().resolveP1());
    }

    @Test
    void testP2() {
        assertEquals("MCD", new Day5().resolveP2());
    }
}
