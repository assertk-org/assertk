package assertk.assertions

import assertk.Assert
import assertk.ValueAssert
import assertk.all

/**
 * Returns an assert on the throwable's message.
 */
fun Assert<Throwable>.message() = prop("message", Throwable::message)

/**
 * Returns an assert on the throwable's cause.
 */
fun Assert<Throwable>.cause() = prop("cause", Throwable::cause)

/**
 * Returns an assert on the throwable's root cause.
 */
fun Assert<Throwable>.rootCause() = prop("rootCause", Throwable::rootCause)

/**
 * Asserts the throwable has the expected message.
 */
fun Assert<Throwable>.hasMessage(message: String?) {
    message().isEqualTo(message)
}

/**
 * Asserts the throwable is similar to the expected cause, checking the type and message.
 * @see [hasNoCause]
 */
fun Assert<Throwable>.hasCause(cause: Throwable) {
    cause().isNotNull().all {
        kClass().isEqualTo(cause::class)
        hasMessage(cause.message)
    }
}

/**
 * Asserts the throwable has no cause.
 * @see [hasCause]
 */
fun Assert<Throwable>.hasNoCause() {
    cause().isNull()
}

/**
 * Asserts the throwable is similar to the expected root cause, checking the type and message.
 */
fun Assert<Throwable>.hasRootCause(cause: Throwable) {
    rootCause().all {
        kClass().isEqualTo(cause::class)
        hasMessage(cause.message)
    }
}

private fun Throwable.rootCause(): Throwable = this.cause?.rootCause() ?: this

