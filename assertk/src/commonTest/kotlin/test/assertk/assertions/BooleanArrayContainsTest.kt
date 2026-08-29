package test.assertk.assertions

import assertk.assertThat
import assertk.assertions.*
import test.assertk.opentestPackageName
import assertk.assertions.support.show
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Handwritten BooleanArray version of the templated tests generated from
 * `assertk/src/testTemplate/test/assertk/assertions/PrimitiveArrayContainsTest.kt`.
 * BooleanArray can't flow from that template because those tests build values with numeric
 * conversions that Boolean lacks. When the template changes, update this file to match.
 */
class BooleanArrayContainsTest {
    //region contains
    @Test
    fun contains_element_present_passes() {
        assertThat(booleanArrayOf(false, true)).contains(true)
        assertThat(booleanArrayOf(false, true)).contains(false)
    }

    @Test
    fun contains_element_missing_fails() {
        val falseError = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf()).contains(false)
        }
        assertEquals("expected to contain:<${show(false, "")}> but was:<[]>", falseError.message)

        val trueError = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf()).contains(false)
        }
        assertEquals("expected to contain:<${show(false, "")}> but was:<[]>", trueError.message)
    }
    //endregion

    //region doesNotContain
    @Test
    fun doesNotContain_element_missing_passes() {
        assertThat(booleanArrayOf()).doesNotContain(false)
        assertThat(booleanArrayOf()).doesNotContain(true)
    }

    @Test
    fun doesNotContain_element_present_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true)).doesNotContain(true)
        }
        assertEquals("expected to not contain:<${show(true, "")}> but was:<[${show(false, "")}, ${show(true, "")}]>", error.message)
    }
    //endregion

    //region containsNone
    @Test
    fun containsNone_missing_elements_passes() {
        assertThat(booleanArrayOf()).containsNone(false)
        assertThat(booleanArrayOf()).containsNone(true)
    }

    @Test
    fun containsNone_present_element_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true)).containsNone(true, true)
        }
        assertEquals(
            """expected to contain none of:<[${show(true, "")}, ${show(true, "")}]> but was:<[${show(false, "")}, ${show(true, "")}]>
                | elements not expected:<[${show(true, "")}, ${show(true, "")}]>
            """.trimMargin(), error.message
        )
    }
    //region

    //region containsAtLeast
    @Test
    fun containsAtLeast_all_elements_passes() {
        assertThat(booleanArrayOf(false, true)).containsAtLeast(true, false)
    }

    @Test
    fun containsAtLeast_some_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false)).containsAtLeast(false, true)
        }
        assertEquals(
            """expected to contain all:<[${show(false, "")}, ${show(true, "")}]> but was:<[${show(false, "")}]>
                | elements not found:<[${show(true, "")}]>
            """.trimMargin(), error.message
        )
    }
    //endregion

    //region containsOnly
    @Test
    fun containsOnly_only_elements_passes() {
        assertThat(booleanArrayOf(false, true)).containsOnly(true, false)
    }

    @Test
    fun containsOnly_duplicate_elements_passes() {
        assertThat(booleanArrayOf(false, true, true)).containsOnly(true, false)
    }

    @Test
    fun containsOnly_duplicate_elements_passes2() {
        assertThat(booleanArrayOf(false, true)).containsOnly(true, true, false)
    }

    @Test
    fun containsOnly_more_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true)).containsOnly(false)
        }
        assertEquals(
            """expected to contain only:<[${show(false, "")}]> but was:<[${show(false, "")}, ${show(true, "")}]>
                | extra elements found:<[${show(true, "")}]>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsOnly_less_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(true)).containsOnly(true, false)
        }
        assertEquals(
            """expected to contain only:<[${show(true, "")}, ${show(false, "")}]> but was:<[${show(true, "")}]>
                | elements not found:<[${show(false, "")}]>
            """.trimMargin(),
            error.message
        )
    }

    @Test
    fun containsOnly_different_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false)).containsOnly(true)
        }
        assertEquals(
            """expected to contain only:<[${show(true, "")}]> but was:<[${show(false, "")}]>
                | elements not found:<[${show(true, "")}]>
                | extra elements found:<[${show(false, "")}]>
            """.trimMargin(),
            error.message
        )
    }
    //endregion

    //region containsExactly
    @Test
    fun containsExactly_all_elements_in_same_order_passes() {
        assertThat(booleanArrayOf(false, true)).containsExactly(false, true)
    }

    @Test
    fun containsExactly_all_elements_in_different_order_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true)).containsExactly(true, false)
        }
        assertEquals(
            """expected to contain exactly:<${show(listOf(true, false), "")}> but was:<${show(listOf(false, true), "")}>
                | at index:0 expected:<${show(true, "")}>
                | at index:1 unexpected:<${show(true, "")}>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactly_missing_element_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, false)).containsExactly(true)
        }
        assertEquals(
            """expected to contain exactly:<${show(listOf(true), "")}> but was:<${show(listOf(false, false), "")}>
                | at index:0 expected:<${show(true, "")}>
                | at index:0 unexpected:<${show(false, "")}>
                | at index:1 unexpected:<${show(false, "")}>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactly_same_indexes_are_together() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, false)).containsExactly(true, true)
        }
        assertEquals(
            """expected to contain exactly:<${show(listOf(true, true), "")}> but was:<${show(listOf(false, false), "")}>
                | at index:0 expected:<${show(true, "")}>
                | at index:0 unexpected:<${show(false, "")}>
                | at index:1 expected:<${show(true, "")}>
                | at index:1 unexpected:<${show(false, "")}>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactly_extra_element_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true)).containsExactly(false, true, false)
        }
        assertEquals(
            """expected to contain exactly:<${show(listOf(false, true, false), "")}> but was:<${show(listOf(false, true), "")}>
                | at index:2 expected:<${show(false, "")}>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactly_missing_element_in_middle_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, false)).containsExactly(false, true, false)
        }
        assertEquals(
            """expected to contain exactly:<${show(listOf(false, true, false), "")}> but was:<${show(listOf(false, false), "")}>
                | at index:1 expected:<${show(true, "")}>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactly_extra_element_in_middle_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true, false)).containsExactly(false, false)
        }
        assertEquals(
            """expected to contain exactly:<${show(listOf(false, false), "")}> but was:<${show(listOf(false, true, false), "")}>
                | at index:1 unexpected:<${show(true, "")}>
            """.trimMargin(), error.message
        )
    }
    //endregion

    //region containsExactlyInAnyOrder
    @Test
    fun containsExactlyInAnyOrder_only_elements_passes() {
        assertThat(booleanArrayOf(false, true)).containsExactlyInAnyOrder(true, false)
    }

    @Test
    fun containsExactlyInAnyOrder_only_elements_passes2() {
        assertThat(booleanArrayOf(false, true, false)).containsExactlyInAnyOrder(true, false, false)
    }

    @Test
    fun containsExactlyInAnyOrder_duplicate_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true, true)).containsExactlyInAnyOrder(true, false)
        }
        assertEquals(
                """expected to contain exactly in any order:<${show(listOf(true, false), "")}> but was:<${show(listOf(false, true, true), "")}>
                | extra elements found:<[${show(true, "")}]>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactlyInAnyOrder_duplicate_elements_fails2() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true)).containsExactlyInAnyOrder(true, true, false)
        }
        assertEquals(
                """expected to contain exactly in any order:<${show(listOf(true, true, false), "")}> but was:<${show(listOf(false, true), "")}>
                | elements not found:<[${show(true, "")}]>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactlyInAnyOrder_more_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true, false)).containsExactlyInAnyOrder(true, false)
        }
        assertEquals(
                """expected to contain exactly in any order:<${show(listOf(true, false), "")}> but was:<${show(listOf(false, true, false), "")}>
                | extra elements found:<[${show(false, "")}]>
            """.trimMargin(), error.message
        )
    }

    @Test
    fun containsExactlyInAnyOrder_less_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true, false)).containsExactlyInAnyOrder(true, false, false, true)
        }
        assertEquals(
                """expected to contain exactly in any order:<${show(listOf(true, false, false, true), "")}> but was:<${show(listOf(false, true, false), "")}>
                | elements not found:<[${show(true, "")}]>
            """.trimMargin(),
                error.message
        )
    }

    @Test
    fun containsExactlyInAnyOrder_different_elements_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false)).containsExactlyInAnyOrder(true)
        }
        assertEquals(
                """expected to contain exactly in any order:<[${show(true, "")}]> but was:<[${show(false, "")}]>
                | elements not found:<[${show(true, "")}]>
                | extra elements found:<[${show(false, "")}]>
            """.trimMargin(),
                error.message
        )
    }
    //endregion

    //region each
    @Test
    fun each_empty_list_passes() {
        assertThat(booleanArrayOf()).each { it.isEqualTo(1) }
    }

    @Test
    fun each_content_passes() {
        assertThat(booleanArrayOf(true, true)).each { it.isGreaterThan(false) }
    }

    @Test
    fun each_non_matching_content_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true, true)).each { it.isLessThan(true) }
        }
        assertEquals(
            """The following assertions failed (2 failures)
                |	${opentestPackageName}AssertionFailedError: expected [[1]] to be less than:<${show(true, "")}> but was:<${show(true, "")}> ([${show(false, "")}, ${show(true, "")}, ${show(true, "")}])
                |	${opentestPackageName}AssertionFailedError: expected [[2]] to be less than:<${show(true, "")}> but was:<${show(true, "")}> ([${show(false, "")}, ${show(true, "")}, ${show(true, "")}])
            """.trimMargin().lines(), error.message!!.lines()
        )
    }
    //endregion

    //region index
    @Test
    fun index_successful_assertion_passes() {
        assertThat(booleanArrayOf(false, true), name = "subject").index(0).isEqualTo(false)
    }

    @Test
    fun index_unsuccessful_assertion_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true), name = "subject").index(0).isGreaterThan(true)
        }
        assertEquals(
            "expected [subject[0]] to be greater than:<${show(true, "")}> but was:<${show(false, "")}> ([${show(false, "")}, ${show(true, "")}])",
            error.message
        )
    }

    @Test
    fun index_out_of_range_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false, true), name = "subject").index(-1).isEqualTo(listOf(false))
        }
        assertEquals("expected [subject] index to be in range:[0-2) but was:<-1>", error.message)
    }
    //endregion
}
