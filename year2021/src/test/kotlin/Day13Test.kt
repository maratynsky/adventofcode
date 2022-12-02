import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13Test {

    @Test
    fun p1test() {
        assertEquals(17, d13p1())
    }

    @Test
    fun p2test() {
        assertEquals(
            """
            █████
                █   █
                █   █
                █   █
                █████
            """.trimIndent(), d13p2().trim()
        )
    }
}