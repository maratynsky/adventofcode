import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    @Test
    void testP1() {
        assertEquals(15, new Day2().resolveP1());
    }

    @Test
    void testP2() {
        assertEquals(12, new Day2().resolveP2());
    }
}
