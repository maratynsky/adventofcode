import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14Test {

    @Test
    fun p1test() {
        assertEquals(1588, d14(10))
    }

    @Test
    fun p2test() {
        assertEquals(2188189693529, d14(40))
    }
}