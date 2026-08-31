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
 * `assertk/src/testTemplate/test/assertk/assertions/PrimitiveArrayTest.kt`.
 * BooleanArray can't flow from that template because those tests build values with numeric
 * conversions that Boolean lacks. When the template changes, update this file to match.
 */
class BooleanArrayTest {
    //region isEqualTo
    @Test fun isEqualTo_same_contents_passes() {
        assertThat(booleanArrayOf(false)).isEqualTo(booleanArrayOf(false))
    }

    @Test fun isEqualTo_different_contents_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false)).isEqualTo(booleanArrayOf(true))
        }
        assertEquals("expected:<[[tru]e]> but was:<[[fals]e]>", error.message)
    }
    //endregion

    //region isNotEqualTo
    @Test
    fun isNotEqualTo_different_contents_passes() {
        assertThat(booleanArrayOf(false)).isNotEqualTo(booleanArrayOf(true))
    }

    @Test
    fun isNotEqualTo_same_contents_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false)).isNotEqualTo(booleanArrayOf(false))
        }
        assertEquals("expected to not be equal to:<[${show(false, "")}]>", error.message)
    }
    //endregion

    //region isEmpty
    @Test
    fun isEmpty_empty_passes() {
        assertThat(booleanArrayOf()).isEmpty()
    }

    @Test
    fun isEmpty_non_empty_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false)).isEmpty()
        }
        assertEquals("expected to be empty but was:<[${show(false, "")}]>", error.message)
    }
    //endregion

    //region isNotEmpty
    @Test
    fun isNotEmpty_non_empty_passes() {
        assertThat(booleanArrayOf(false)).isNotEmpty()
    }

    @Test
    fun isNotEmpty_empty_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf()).isNotEmpty()
        }
        assertEquals("expected to not be empty", error.message)
    }
    //endregion

    //region isNullOrEmpty
    @Test
    fun isNullOrEmpty_null_passes() {
        assertThat(null as BooleanArray?).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_empty_passes() {
        assertThat(booleanArrayOf()).isNullOrEmpty()
    }

    @Test
    fun isNullOrEmpty_non_empty_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf(false)).isNullOrEmpty()
        }
        assertEquals("expected to be null or empty but was:<[${show(false, "")}]>", error.message)
    }
    //endregion

    //region hasSize
    @Test
    fun hasSize_correct_size_passes() {
        assertThat(booleanArrayOf()).hasSize(0)
    }

    @Test
    fun hasSize_wrong_size_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf()).hasSize(1)
        }
        assertEquals("expected [size]:<[1]> but was:<[0]> ([])", error.message)
    }
    //endregion

    //region hasSameSizeAs
    @Test
    fun hasSameSizeAs_equal_sizes_passes() {
        assertThat(booleanArrayOf()).hasSameSizeAs(booleanArrayOf())
    }

    @Test
    fun hasSameSizeAs_non_equal_sizes_fails() {
        val error = assertFailsWith<AssertionError> {
            assertThat(booleanArrayOf()).hasSameSizeAs(booleanArrayOf(false))
        }
        assertEquals("expected to have same size as:<[${show(false, "")}]> (1) but was size:(0)", error.message)
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
