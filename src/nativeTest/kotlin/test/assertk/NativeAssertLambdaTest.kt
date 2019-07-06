package test.assertk

import assertk.assertThat
import assertk.assertions.*
import assertk.catch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class NativeAssertLambdaTest {

    @UseExperimental(ExperimentalCoroutinesApi::class)
    @Test fun returnedValue_works_in_coroutine_test() {
        runBlocking {
            assertThat {
                asyncReturnValue()
            }.isSuccess().isEqualTo(1)
        }
    }

    @UseExperimental(ExperimentalCoroutinesApi::class)
    @Test fun returnedValue_exception_works_in_coroutine_test() {
        runBlocking {
            assertThat {
                asyncThrows()
            }.isFailure().hasMessage("test")
        }
    }

    @UseExperimental(ExperimentalCoroutinesApi::class)
    @Test fun catch_works_in_coroutine_test() {
        runBlocking {
            val error = catch {
                asyncThrows()
            }
            assertThat(error).isNotNull().hasMessage("test")
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
