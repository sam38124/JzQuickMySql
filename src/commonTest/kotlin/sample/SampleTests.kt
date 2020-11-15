package sample

import kotlin.test.Test
import kotlin.test.assertTrue
import sample.*
class SampleTests {
    @Test
    fun testMe() {
        assertTrue(Sample().checkMe() > 0)
        println(hello())

    }
}
