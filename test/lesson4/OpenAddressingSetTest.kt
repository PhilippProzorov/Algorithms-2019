package lesson4

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag

class OpenAddressingSetTest {

    @Test
    @Tag("Example")
    fun add() {
        val set = OpenAddressingSet<String>(16)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        assertSame(3, set.size)
        assertTrue("Beta" in set)
        assertFalse("Gamma" in set)
        assertTrue("Omega" in set)
    }

    @Test
    @Tag("Basic")
    fun remove() {
        val set = OpenAddressingSet<String>(16)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        val removal = set.remove("Alpha")
        assertSame(true, removal)
        assertSame(2, set.size)
        assertTrue("Beta" in set)
        assertTrue("Omega" in set)
        assertFalse("Gamma" in set)
    }

    @Test
    @Tag("Basic")
    fun iterator() {
        val set = OpenAddressingSet<String>(16)
        assertTrue(set.isEmpty())
        set.add("Alpha")
        set.add("Beta")
        set.add("Omega")
        assertTrue("Alpha" in set)
        assertTrue("Beta" in set)
        assertTrue("Omega" in set)
        var size = set.size
        val iterator = set.iterator()
        while (iterator.hasNext()) {
            assertTrue(iterator.next() in set)
            size -= 1
        }
        assertSame(0, size)
    }
}