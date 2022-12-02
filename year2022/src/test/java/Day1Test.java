import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    @Test
    void testP1() {
        assertEquals(24000, new Day1().resolveP1());
    }

    @Test
    void testP2() {
        assertEquals(45000, new Day1().resolveP2());
    }
}
