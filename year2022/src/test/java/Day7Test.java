import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    @Test
    void testP1() {
        assertEquals(95437, new Day7().resolveP1());
    }

    @Test
    void testP2() {
        assertEquals(24933642, new Day7().resolveP2());
    }
}
