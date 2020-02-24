package lesson4

@Suppress("UNCHECKED_CAST")
class OpenAddressingSet<T : Any>(private val bits: Int) : AbstractMutableSet<T>() {
    init {
        require(bits in 2..31)
    }

    private object ObjectRemoved

    private var counter = 0

    private val capacity = 1 shl bits

    private val storage = Array<Any?>(capacity) { null }

    override var size: Int = 0

    private fun T.startingIndex(): Int {
        return hashCode() and (0x7FFFFFFF shr (31 - bits))
    }

    override fun contains(element: T): Boolean {
        var index = element.startingIndex()
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                return true
            }
            index = (index + 1) % capacity
            current = storage[index]
        }
        return false
    }

    override fun add(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                return false
            }
            index = (index + 1) % capacity
            check(index != startingIndex) { "Table is full" }
            current = storage[index]
        }
        storage[index] = element
        size++
        return true
    }

    /**
     * Для этой задачи пока нет тестов, но вы можете попробовать привести решение и добавить к нему тесты
     */
    override fun remove(element: T): Boolean {
        //Трудоёмкость O(n)
        //n - val capacity
        //Ресурсоёмкость O(1)
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                storage[index] = null
                size--
                return true
            }
            index++ % capacity
            if (index == startingIndex) {
                return false
            }
            current = storage[index]
        }
        return false
    }

    private fun removeAt(index: Int) {
        storage[index] = ObjectRemoved
        size--
        counter++
    }

    /**
     * Для этой задачи пока нет тестов, но вы можете попробовать привести решение и добавить к нему тесты
     */
    override fun iterator(): MutableIterator<T> {
        return OpenAddressingSetIterator()
    }

    internal inner class OpenAddressingSetIterator : MutableIterator<T> {
        var estimate: Int = counter
        var next: T? = findNext()
        var nextIndex = 0
        var current = 0
        var removed = false
        override fun next(): T {
            //Трудоёмкость O(n)
            //n - val capacity
            //Ресурсоёмкость O(1)
            val index = next
            if (estimate != counter) {
                throw ConcurrentModificationException()
            }
            if (next == null) {
                throw NoSuchElementException()
            }
            current = nextIndex
            next = findNext()
            removed = false
            return index ?: throw NullPointerException("Expression 'index' must not be null")
        }

        private fun findNext(): T? {
            return if (size == 0) {
                null
            } else {
                var current: Any?
                do {
                    nextIndex++
                    if (nextIndex == capacity) {
                        return null
                    }
                    current = storage[nextIndex]
                } while (current == null || current == ObjectRemoved)
                current as T?
            }
        }

        override fun hasNext(): Boolean {
            return next != null
        }

        override fun remove() {
            //Трудоёмкость O(1)
            //Ресурсоёмкость O(1)
            if (estimate != counter) {
                throw ConcurrentModificationException()
            }
            check(!removed)
            removeAt(current)
            estimate = counter
            removed = true
        }
    }
}