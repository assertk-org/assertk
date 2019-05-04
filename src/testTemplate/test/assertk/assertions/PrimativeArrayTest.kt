package test.assertk.assertions

import assertk.assertThat
import assertk.assertions.*
import assertk.assertions.support.show
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

$T:$N:$E = ByteArray:byteArray:Byte, IntArray:intArray:Int, ShortArray:shortArray:Short, LongArray:longArray:Long, FloatArray:floatArray:Float, DoubleArray:doubleArray:Double, CharArray:charArray:Char

class $TTest {
    //region isEqualTo
    @Test fun isEqualTo_same_contents_passes() {
        assertThat($NOf(0.to$E())).isEqualTo($NOf(0.to$E()))
    }

//    @Test fun isEqualTo_different_contents_fails() {
//        val error = assertFails {
//            assertThat($NOf(97.to$E())).isEqualTo($NOf(98.to$E()))
//        }
//        assertEquals("expected:<[[${show(98.to$E(), "")}]]> but was:<[[${show(97.to$E(), "")}]]>", error.message)
//    }
    //endregion

    //region isNotEqualTo
    @Test fun isNotEqualTo_different_contents_passes() {
        assertThat($NOf(0.to$E())).isNotEqualTo($NOf(1.to$E()))
    }

    @Test fun isNotEqualTo_same_contents_fails() {
        val error = assertFails {
            assertThat($NOf(0.to$E())).isNotEqualTo($NOf(0.to$E()))
        }
        assertEquals("expected to not be equal to:<[${show(0.to$E(), "")}]>", error.message)
    }
    //endregion

    //region isEmpty
    @Test fun isEmpty_empty_passes() {
        assertThat($NOf()).isEmpty()
    }

    @Test fun isEmpty_non_empty_fails() {
        val error = assertFails {
            assertThat($NOf(0.to$E())).isEmpty()
        }
        assertEquals("expected to be empty but was:<[${show(0.to$E(), "")}]>", error.message)
    }
    //endregion

    //region isNotEmpty
    @Test fun isNotEmpty_non_empty_passes() {
        assertThat($NOf(0.to$E())).isNotEmpty()
    }

    @Test fun isNotEmpty_empty_fails() {
        val error = assertFails {
            assertThat($NOf()).isNotEmpty()
        }
        assertEquals("expected to not be empty", error.message)
    }
    //endregion

    //region isNullOrEmpty
    @Test fun isNullOrEmpty_null_passes() {
        assertThat(null as $T?).isNullOrEmpty()
    }

    @Test fun isNullOrEmpty_empty_passes() {
        assertThat($NOf()).isNullOrEmpty()
    }

    @Test fun isNullOrEmpty_non_empty_fails() {
        val error = assertFails {
            assertThat($NOf(0.to$E())).isNullOrEmpty()
        }
        assertEquals("expected to be null or empty but was:<[${show(0.to$E(), "")}]>", error.message)
    }
    //endregion

    //region hasSize
    @Test fun hasSize_correct_size_passes() {
        assertThat($NOf()).hasSize(0)
    }

    @Test fun hasSize_wrong_size_fails() {
        val error = assertFails {
            assertThat($NOf()).hasSize(1)
        }
        assertEquals("expected [size]:<[1]> but was:<[0]> ([])", error.message)
    }
    //endregion

    //region hasSameSizeAs
    @Test fun hasSameSizeAs_equal_sizes_passes() {
        assertThat($NOf()).hasSameSizeAs($NOf())
    }

    @Test fun hasSameSizeAs_non_equal_sizes_fails() {
        val error = assertFails {
            assertThat($NOf()).hasSameSizeAs($NOf(0.to$E()))
        }
        assertEquals("expected to have same size as:<[${show(0.to$E(), "")}]> (1) but was size:(0)", error.message)
    }
    //endregion

    //region contains
    @Test fun contains_element_present_passes() {
        assertThat($NOf(1.to$E(), 2.to$E())).contains(2.to$E())
    }

    @Test fun contains_element_missing_fails() {
        val error = assertFails {
            assertThat($NOf()).contains(1.to$E())
        }
        assertEquals("expected to contain:<${show(1.to$E(), "")}> but was:<[]>", error.message)
    }
    //endregion

    //region doesNotContain
    @Test fun doesNotContain_element_missing_passes() {
        assertThat($NOf()).doesNotContain(1.to$E())
    }

    @Test fun doesNotContain_element_present_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E())).doesNotContain(2.to$E())
        }
        assertEquals("expected to not contain:<${show(2.to$E(), "")}> but was:<[${show(1.to$E(), "")}, ${show(2.to$E(), "")}]>", error.message)
    }
    //endregion

    //region containsNone
    @Test fun containsNone_missing_elements_passes() {
        assertThat($NOf()).containsNone(1.to$E())
    }

    @Test fun containsNone_present_element_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E())).containsNone(2.to$E(), 3.to$E())
        }
        assertEquals("expected to contain none of:<[${show(2.to$E(), "")}, ${show(3.to$E(), "")}]> some elements were not expected:<[${show(2.to$E(), "")}]>", error.message)
    }
    //region

    //region containsAll
    @Test fun containsAll_all_elements_passes() {
        assertThat($NOf(1.to$E(), 2.to$E())).containsAll(2.to$E(), 1.to$E())
    }

    @Test fun containsAll_some_elements_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E())).containsAll(1.to$E(), 2.to$E())
        }
        assertEquals("expected to contain all:<[${show(1.to$E(), "")}, ${show(2.to$E(), "")}]> some elements were not found:<[${show(2.to$E(), "")}]>", error.message)
    }
    //endregion

    //region containsExactly
    @Test fun containsExactly_all_elements_in_same_order_passes() {
        assertThat($NOf(1.to$E(), 2.to$E())).containsExactly(1.to$E(), 2.to$E())
    }

    @Test fun containsExactly_all_elements_in_different_order_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E())).containsExactly(2.to$E(), 1.to$E())
        }
        assertEquals(
            """expected to contain exactly:
                | at index:0 expected:<${show(2.to$E(), "")}>
                | at index:1 unexpected:<${show(2.to$E(), "")}>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactly_missing_element_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E())).containsExactly(3.to$E())
        }
        assertEquals(
            """expected to contain exactly:
                | at index:0 expected:<${show(3.to$E(), "")}>
                | at index:0 unexpected:<${show(1.to$E(), "")}>
                | at index:1 unexpected:<${show(2.to$E(), "")}>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactly_same_indexes_are_together() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 1.to$E())).containsExactly(2.to$E(), 2.to$E())
        }
        assertEquals(
            """expected to contain exactly:
                | at index:0 expected:<${show(2.to$E(), "")}>
                | at index:0 unexpected:<${show(1.to$E(), "")}>
                | at index:1 expected:<${show(2.to$E(), "")}>
                | at index:1 unexpected:<${show(1.to$E(), "")}>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactly_extra_element_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E())).containsExactly(1.to$E(), 2.to$E(), 3.to$E())
        }
        assertEquals(
            """expected to contain exactly:
                | at index:2 expected:<${show(3.to$E(), "")}>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactly_missing_element_in_middle_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 3.to$E())).containsExactly(1.to$E(), 2.to$E(), 3.to$E())
        }
        assertEquals(
            """expected to contain exactly:
                | at index:1 expected:<${show(2.to$E(), "")}>
            """.trimMargin(), error.message
        )
    }

    @Test fun containsExactly_extra_element_in_middle_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E(), 3.to$E())).containsExactly(1.to$E(), 3.to$E())
        }
        assertEquals(
            """expected to contain exactly:
                | at index:1 unexpected:<${show(2.to$E(), "")}>
            """.trimMargin(), error.message
        )
    }
    //endregion

    //region each
    @Test fun each_empty_list_passes() {
        assertThat($NOf()).each { it.isEqualTo(1) }
    }

    @Test fun each_content_passes() {
        assertThat($NOf(1.to$E(), 2.to$E())).each { it.isGreaterThan(0.to$E()) }
    }

    @Test fun each_non_matching_content_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E(), 3.to$E())).each { it.isLessThan(2.to$E()) }
        }
        assertEquals(
            """The following assertions failed (2 failures)
                |	expected [[1]] to be less than:<${show(2.to$E(), "")}> but was:<${show(2.to$E(), "")}> ([${show(1.to$E(), "")}, ${show(2.to$E(), "")}, ${show(3.to$E(), "")}])
                |	expected [[2]] to be less than:<${show(2.to$E(), "")}> but was:<${show(3.to$E(), "")}> ([${show(1.to$E(), "")}, ${show(2.to$E(), "")}, ${show(3.to$E(), "")}])
            """.trimMargin(), error.message
        )
    }
    //endregion

    //region index
    @Test fun index_successful_assertion_passes() {
        assertThat($NOf(1.to$E(), 2.to$E()), name = "subject").index(0).isEqualTo(1.to$E())
    }

    @Test fun index_unsuccessful_assertion_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E()), name = "subject").index(0).isGreaterThan(2.to$E())
        }
        assertEquals(
            "expected [subject[0]] to be greater than:<${show(2.to$E(), "")}> but was:<${show(1.to$E(), "")}> ([${show(1.to$E(), "")}, ${show(2.to$E(), "")}])",
            error.message
        )
    }

    @Test fun index_out_of_range_fails() {
        val error = assertFails {
            assertThat($NOf(1.to$E(), 2.to$E()), name = "subject").index(-1).isEqualTo(listOf(1.to$E()))
        }
        assertEquals("expected [subject] index to be in range:[0-2) but was:<-1>", error.message)
    }
    //endregion
}
