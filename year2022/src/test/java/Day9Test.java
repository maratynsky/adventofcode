import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    @Test
    void testP1() {
        assertEquals(13, new Day9().resolveP1());
    }

    @Test
    void testP2() {
        assertEquals(36, new Day9().withInput("Day9.2.in").resolveP2());
    }
}
