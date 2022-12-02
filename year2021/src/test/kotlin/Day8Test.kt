import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day8Test {

    @Test
    fun p1test() {
        assertEquals(26, d8p1())
    }

    @Test
    fun p2test() {
        assertEquals(61229, d8p2())
    }

    @Test
    fun subtractTest() {
        assertEquals("ad", subtract("abcd", "bc"))
    }
}