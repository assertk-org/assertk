package assertk.assertions

import assertk.Assert
import assertk.PlatformName
import assertk.all
import assertk.assertions.support.expected
import assertk.assertions.support.show
import assertk.assertions.support.fail

$T:$N:$E = ByteArray:byteArray:Byte, IntArray:intArray:Int, ShortArray:shortArray:Short, LongArray:longArray:Long, FloatArray:floatArray:Float, DoubleArray:doubleArray:Double, CharArray:charArray:Char

/**
 * Returns an assert on the $T's size.
 */
@PlatformName("$NSize")
fun Assert<$T>.size() = prop("size") { it.size }

/**
 * Asserts the $T contents are equal to the expected one, using [contentDeepEquals].
 * @see isNotEqualTo
 */
fun Assert<$T>.isEqualTo(expected: $T) = given { actual ->
    if (actual.contentEquals(expected)) return
    fail(expected, actual)
}

/**
 * Asserts the $T contents are not equal to the expected one, using [contentDeepEquals].
 * @see isEqualTo
 */
fun Assert<$T>.isNotEqualTo(expected: $T) = given { actual ->
    if (!(actual.contentEquals(expected))) return
    val showExpected = show(expected)
    val showActual = show(actual)
    // if they display the same, only show one.
    if (showExpected == showActual) {
        expected("to not be equal to:$showActual")
    } else {
        expected(":$showExpected not to be equal to:$showActual")
    }
}

/**
 * Asserts the $T is empty.
 * @see [isNotEmpty]
 * @see [isNullOrEmpty]
 */
@PlatformName("$NIsEmpty")
fun Assert<$T>.isEmpty() = given { actual ->
    if (actual.isEmpty()) return
    expected("to be empty but was:${show(actual)}")
}

/**
 * Asserts the $T is not empty.
 * @see [isEmpty]
 */
@PlatformName("$NIsNotEmpty")
fun Assert<$T>.isNotEmpty() = given { actual ->
    if (actual.isNotEmpty()) return
    expected("to not be empty")
}

/**
 * Asserts the $T is null or empty.
 * @see [isEmpty]
 */
@PlatformName("$NIsNullOrEmpty")
fun Assert<$T?>.isNullOrEmpty() = given { actual ->
    if (actual == null || actual.isEmpty()) return
    expected("to be null or empty but was:${show(actual)}")
}

/**
 * Asserts the $T has the expected size.
 */
@PlatformName("$NHasSize")
fun Assert<$T>.hasSize(size: Int) {
    size().isEqualTo(size)
}

/**
 * Asserts the $T has the same size as the expected array.
 */
@PlatformName("$NHasSameSizeAs")
fun Assert<$T>.hasSameSizeAs(other: $T) = given { actual ->
    val actualSize = actual.size
    val otherSize = other.size
    if (actualSize == otherSize) return
    expected("to have same size as:${show(other)} ($otherSize) but was size:($actualSize)")
}

/**
 * Asserts the $T contains the expected element, using `in`.
 * @see [doesNotContain]
 */
@PlatformName("$NContains")
fun Assert<$T>.contains(element: $E) = given { actual ->
    if (element in actual) return
    expected("to contain:${show(element)} but was:${show(actual)}")
}

/**
 * Asserts the $T does not contain the expected element, using `!in`.
 * @see [contains]
 */
@PlatformName("$NDoesNotContain")
fun Assert<$T>.doesNotContain(element: $E) = given { actual ->
    if (element !in actual) return
    expected("to not contain:${show(element)} but was:${show(actual)}")
}

/**
 * Asserts the $T does not contain any of the expected elements.
 * @see [containsAll]
 */
fun Assert<$T>.containsNone(vararg elements: $E) = given { actual ->
    if (elements.none { it in actual }) {
        return
    }

    val notExpected = elements.filter { it in actual }
    expected("to contain none of:${show(elements)} some elements were not expected:${show(notExpected)}")
}

/**
 * Asserts the $T contains all the expected elements, in any order. The array may also contain
 * additional elements.
 * @see [containsExactly]
 */
@PlatformName("$NContainsAll")
fun Assert<$T>.containsAll(vararg elements: $E) = given { actual ->
    if (elements.all { actual.contains(it) }) return
    val notFound = elements.filterNot { it in actual }
    expected("to contain all:${show(elements)} some elements were not found:${show(notFound)}")
}

/**
 * Returns an assert that assertion on the value at the given index in the array.
 *
 * ```
 * assertThat($NOf(0, 1, 2)).index(1) { it.isPositive() }
 * ```
 */
@PlatformName("$NIndexOld")
@Deprecated(message = "Use index(index) instead.", replaceWith = ReplaceWith("index(index).let(f)"), level = DeprecationLevel.ERROR)
fun Assert<$T>.index(index: Int, f: (Assert<$E>) -> Unit) {
    index(index).let(f)
}

/**
 * Returns an assert that assertion on the value at the given index in the array.
 *
 * ```
 * assertThat($NOf(0, 1, 2)).index(1).isPositive()
 * ```
 */
@PlatformName("$NIndex")
fun Assert<$T>.index(index: Int): Assert<$E> =
    transform("${name ?: ""}${show(index, "[]")}") { actual ->
        if (index in 0 until actual.size) {
            actual[index]
        } else {
            expected("index to be in range:[0-${actual.size}) but was:${show(index)}")
        }
    }

/**
 * Asserts the $T contains exactly the expected elements. They must be in the same order and
 * there must not be any extra elements.
 * @see [containsAll]
 */
@PlatformName("$NContainsExactly")
fun Assert<$T>.containsExactly(vararg elements: $E) = given { actual ->
    if (actual.contentEquals(elements)) return

    expected(listDifferExpected(elements.toList(), actual.toList()))
}

/**
 * Asserts on each item in the $T. The given lambda will be run for each item.
 *
 * ```
 * assertThat($NOf("one", "two")).each {
 *   it.hasLength(3)
 * }
 * ```
 */
@PlatformName("$NEach")
fun Assert<$T>.each(f: (Assert<$E>) -> Unit) = given { actual ->
    all {
        actual.forEachIndexed { index, item ->
            f(assertThat(item, name = "${name ?: ""}${show(index, "[]")}"))
        }
    }
}
