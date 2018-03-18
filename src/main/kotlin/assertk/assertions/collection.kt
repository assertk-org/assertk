package assertk.assertions

import assertk.Assert
import assertk.assertions.support.expected
import assertk.assertions.support.show

/**
 * Asserts the collection is empty.
 * @see [isNotEmpty]
 * @see [isNullOrEmpty]
 */
fun <T : Collection<*>> Assert<T>.isEmpty() {
    if (actual.isEmpty()) return
    expected("to be empty but was:${show(actual)}")
}

/**
 * Asserts the collection is not empty.
 * @see [isEmpty]
 */
fun <T : Collection<*>> Assert<T>.isNotEmpty() {
    if (actual.isNotEmpty()) return
    expected("to not be empty")
}

/**
 * Asserts the collection is null or empty.
 * @see [isEmpty]
 */
fun <T : Collection<*>?> Assert<T>.isNullOrEmpty() {
    if (actual == null || actual.isEmpty()) return
    expected("to be null or empty but was:${show(actual)}")
}

/**
 * Asserts the collection has the expected size.
 */
fun <T : Collection<*>> Assert<T>.hasSize(size: Int) {
    assert(actual.size, "size").isEqualTo(size)
}

/**
 * Asserts the collection has the same size as the expected collection.
 */
fun <T : Collection<*>> Assert<T>.hasSameSizeAs(other: Collection<*>) {
    val actualSize = actual.size
    val otherSize = other.size
    if (actualSize == otherSize) return
    expected("to have same size as:${show(other)} ($otherSize) but was size:($actualSize)")
}

/**
 * Asserts the collection does not contain any of the expected elements.
 * @see [containsAll]
 */
fun <T : Collection<*>> Assert<T>.containsNone(vararg elements: Any?) {
    if (elements.none { it in actual }) {
        return
    }

    val notExpected = elements.filter { it in actual }
    expected("to contain none of:${show(elements)} but was:${show(actual)}" +
            " some elements were not expected:${show(notExpected)}")
}

/**
 * Asserts the collection contains all the expected elements, in any order. The collection may also
 * contain additional elements.
 * @see [containsNone]
 * @see [containsExactly]
 */
fun <T : Collection<*>> Assert<T>.containsAll(vararg elements: Any?) {
    if (actual.containsAll(elements.toList())) {
        return
    }

    val notFound = elements.filterNot { it in actual }
    expected("to contain all:${show(elements)} but was:${show(actual)}" +
            " some elements were not found:${show(notFound)}")
}

/**
 * Asserts the collection contains exactly the expected elements. They must be in the same order and
 * there must not be any extra elements.
 * @see [containsAll]
 */
fun <T : Collection<*>> Assert<T>.containsExactly(vararg elements: Any?) {
    val actualAsList = actual.toList()
    if (actualAsList == elements.toList()) {
        return
    }

    val firstDifferingIndex = (elements zip actualAsList).indexOfFirst { it.first != it.second }
    val notExpected = actualAsList.toMutableList()
    val notFound = mutableListOf<Any?>()

    for (element in elements) {
        if (element in notExpected) {
            notExpected.removeAt(notExpected.indexOfFirst { it == element })
        } else {
            notFound += element
        }
    }

    if (!notExpected.isEmpty() && !notFound.isEmpty()) {
        expected("to contain exactly:${show(elements)} but was:${show(actual)}" +
                " some elements were not found:${show(notFound)}" +
                " some elements were not expected:${show(notExpected)}")
    } else if (!notFound.isEmpty()) {
        expected("to contain exactly:${show(elements)} but was:${show(actual)}" +
                " some elements were not found:${show(notFound)}")
    } else if (!notExpected.isEmpty()) {
        expected("to contain exactly:${show(elements)} but was:${show(actual)}" +
                " some elements were not expected:${show(notExpected)}")
    } else {
        expected("to contain exactly:${show(elements)} but was:${show(actual)}" +
                " first difference at index $firstDifferingIndex" +
                " expected:${show(elements[firstDifferingIndex])} but was:${show(actualAsList[firstDifferingIndex])}")
    }
}
