import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    @Test
    void testP1() {
        assertEquals(10605, new Day11().resolveP1());
    }

    @Test
    void testP2() {
        assertEquals(2713310158L, new Day11().resolveP2());
    }
}
