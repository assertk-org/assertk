package test.assertk

import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class NativeAssertLambdaTest {

    @Test fun returnedValue_works_in_coroutine_test() {
        runBlocking {
            assertThat {
                asyncReturnValue()
            }.isSuccess().isEqualTo(1)
        }
    }

    @Test fun returnedValue_exception_works_in_coroutine_test() {
        runBlocking {
            assertThat {
                asyncThrows()
            }.isFailure().hasMessage("test")
        }
    }

    @Suppress("RedundantSuspendModifier")
    private suspend fun asyncReturnValue(): Int {
        return 1
    }

    @Suppress("RedundantSuspendModifier")
    private suspend fun asyncThrows() {
        throw  Exception("test")
    }
}