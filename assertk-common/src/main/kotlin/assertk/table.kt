package assertk

import assertk.assertions.support.show

private class TableFailure(private val table: Table) : Failure {
    private val failures: MutableMap<Int, MutableList<AssertionError>> = LinkedHashMap()

    override fun fail(error: AssertionError) {
        failures.getOrPut(table.index, { ArrayList() }).plusAssign(error)
    }

    override fun invoke() {
        if (!failures.isEmpty()) {
            fail(compositeErrorMessage(failures))
        }
    }

    private fun compositeErrorMessage(errors: Map<Int, List<AssertionError>>): String {
        val errorCount = errors.map { it.value.size }.sum()
        val prefix = if (errorCount == 1) {
            "The following assertion failed:\n"
        } else {
            "The following $errorCount assertions failed:\n"
        }
        return errors
                .map {
                    val (index, failures) = it
                    failures.joinToString(
                            transform = { "- ${it.message}" },
                            prefix = rowMessage(index) + "\n",
                            separator = "\n")
                }.joinToString(
                prefix = prefix,
                separator = "\n\n")
    }

    private fun rowMessage(index: Int): String {
        val row = table.rows[index]
        return table.columnNames.mapIndexed { i, name -> Pair(name, row[i]) }.joinToString(
                prefix = "on row:(",
                separator = ",",
                postfix = ")",
                transform = { "${it.first}=${show(it.second)}" })
    }
}

/**
 * A table of rows to assert on. This makes it easy to run the same assertions are a number of inputs and outputs.
 * @see [tableOf]
 */
sealed class Table(internal val columnNames: Array<String>) {
    internal val rows = arrayListOf<Array<out Any?>>()
    internal var index = 0

    protected interface TableFun {
        /**
         * Delegate that receives all values of a row.
         */
        operator fun invoke(values: Array<out Any?>)
    }

    internal fun row(vararg values: Any?) {
        var size: Int? = null
        for (row in rows) {
            if (size == null) {
                size = row.size
            } else {
                if (size != row.size) {
                    throw IllegalArgumentException(
                            "all rows must have the same size. expected:$size but got:${row.size}")
                }
            }
        }
        rows.add(values)
    }

    protected fun forAll(f: TableFun) {
        FailureContext.run(TableFailure(this)) {
            for (i in 0 until rows.size) {
                index = i
                f(rows[i])
            }
        }
    }
}

/**
 * A table with rows of 1 value.
 * @see [tableOf]
 */
class Table1<C1> internal constructor(columnNames: Array<String>) : Table(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun row(val1: C1): Table1<C1> = apply {
        super.row(val1)
    }

    /**
     * Runs the given lambda for each row in the table.
     */
    fun forAll(f: (C1) -> Unit) {
        forAll(object : TableFun {
            override fun invoke(values: Array<out Any?>) {
                @Suppress("UNCHECKED_CAST", "UnsafeCast")
                f(values[0] as C1)
            }
        })
    }
}

/**
 * A table with rows of 2 values.
 * @see [tableOf]
 */
class Table2<C1, C2> internal constructor(columnNames: Array<String>) : Table(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun row(val1: C1, val2: C2): Table2<C1, C2> = apply {
        super.row(val1, val2)
    }

    /**
     * Runs the given lambda for each row in the table.
     */
    fun forAll(f: (C1, C2) -> Unit) {
        forAll(object : TableFun {
            override fun invoke(values: Array<out Any?>) {
                @Suppress("UNCHECKED_CAST", "UnsafeCast")
                f(values[0] as C1, values[1] as C2)
            }
        })
    }
}

/**
 * A table with rows of 3 values.
 * @see [tableOf]
 */
class Table3<C1, C2, C3> internal constructor(columnNames: Array<String>) : Table(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun row(val1: C1, val2: C2, val3: C3): Table3<C1, C2, C3> = apply {
        super.row(val1, val2, val3)
    }

    /**
     * Runs the given lambda for each row in the table.
     */
    fun forAll(f: (C1, C2, C3) -> Unit) {
        forAll(object : TableFun {
            override fun invoke(values: Array<out Any?>) {
                @Suppress("UNCHECKED_CAST", "UnsafeCast")
                f(values[0] as C1, values[1] as C2, values[2] as C3)
            }
        })
    }
}

/**
 * A table with rows of 4 values.
 * @see [tableOf]
 */
class Table4<C1, C2, C3, C4> internal constructor(columnNames: Array<String>) : Table(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun row(val1: C1, val2: C2, val3: C3, val4: C4): Table4<C1, C2, C3, C4> = apply {
        super.row(val1, val2, val3, val4)
    }

    /**
     * Runs the given lambda for each row in the table.
     */
    fun forAll(f: (C1, C2, C3, C4) -> Unit) {
        forAll(object : TableFun {
            override fun invoke(values: Array<out Any?>) {
                @Suppress("UNCHECKED_CAST", "UnsafeCast", "MagicNumber")
                f(values[0] as C1, values[1] as C2, values[2] as C3, values[3] as C4)
            }
        })
    }
}

/**
 * Builds a table with the given column names.
 */
fun tableOf(name1: String): Table1Builder
        = Table1Builder(arrayOf(name1))

/**
 * Builds a table with the given column names.
 */
fun tableOf(name1: String, name2: String): Table2Builder
        = Table2Builder(arrayOf(name1, name2))

/**
 * Builds a table with the given column names.
 */
fun tableOf(name1: String, name2: String, name3: String): Table3Builder
        = Table3Builder(arrayOf(name1, name2, name3))

/**
 * Builds a table with the given column names.
 */
fun tableOf(name1: String, name2: String, name3: String, name4: String): Table4Builder
        = Table4Builder(arrayOf(name1, name2, name3, name4))

/**
 * Builds a table with the given rows.
 */
sealed class TableBuilder(internal val columnNames: Array<String>)

/**
 * Builds a table with the given rows.
 */
class Table1Builder internal constructor(columnNames: Array<String>) : TableBuilder(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun <C1> row(val1: C1): Table1<C1> =
            Table1<C1>(columnNames).apply { row(val1) }
}

/**
 * Builds a table with the given rows.
 */
class Table2Builder internal constructor(columnNames: Array<String>) : TableBuilder(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun <C1, C2> row(val1: C1, val2: C2): Table2<C1, C2> =
            Table2<C1, C2>(columnNames).apply { row(val1, val2) }
}

/**
 * Builds a table with the given rows.
 */
class Table3Builder internal constructor(columnNames: Array<String>) : TableBuilder(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun <C1, C2, C3> row(val1: C1, val2: C2, val3: C3): Table3<C1, C2, C3> =
            Table3<C1, C2, C3>(columnNames).apply { row(val1, val2, val3) }
}

/**
 * Builds a table with the given rows.
 */
class Table4Builder internal constructor(columnNames: Array<String>) : TableBuilder(columnNames) {
    /**
     * Adds a row to the table with the given values.
     */
    fun <C1, C2, C3, C4> row(val1: C1, val2: C2, val3: C3, val4: C4): Table4<C1, C2, C3, C4> =
            Table4<C1, C2, C3, C4>(columnNames).apply { row(val1, val2, val3, val4) }
}

