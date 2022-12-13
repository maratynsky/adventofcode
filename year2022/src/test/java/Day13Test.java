import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day13Test {

    @Test
    void testP1() {
        assertEquals(13, new Day13().resolveP1());
    }

    @Test
    void testP2() {
        assertEquals(140, new Day13().resolveP2());
    }
}
