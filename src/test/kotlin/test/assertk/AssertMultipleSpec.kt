package test.assertk

import assertk.all
import assertk.assert
import assertk.assertAll
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.toStringFun
import org.assertj.core.api.Assertions
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class AssertMultipleSpec : Spek({

    on("an assert block with a value") {
        val subject = BasicObject("test", 1)

        it("should pass multiple successful assertions") {
            assert(subject).all {
                isInstanceOf(BasicObject::class)
                toStringFun().isEqualTo("BasicObject(arg1=test, arg2=1)")
            }
        }

        it("should fail the first assertion") {
            Assertions.assertThatThrownBy {
                assert(subject).all {
                    isInstanceOf(String::class)
                    toStringFun().isEqualTo("BasicObject(arg1=test, arg2=1)")
                }
            }.hasMessage("expected to be instance of:<java.lang.String> but had class:<test.assertk.AssertMultipleSpec\$BasicObject>")
        }

        it("should fail the second assertion") {
            Assertions.assertThatThrownBy {
                assert(subject).all {
                    isInstanceOf(BasicObject::class)
                    toStringFun().isEqualTo("wrong")
                }
            }.hasMessage("expected [toString]:<\"[wrong]\"> but was:<\"[BasicObject(arg1=test, arg2=1)]\"> (BasicObject(arg1=test, arg2=1))")
        }

        it("should fail both assertions") {
            Assertions.assertThatThrownBy {
                assert<Any>(subject).all {
                    isInstanceOf(String::class)
                    toStringFun().isEqualTo("wrong")
                }
            }.hasMessage("""The following 2 assertions failed:
- expected to be instance of:<java.lang.String> but had class:<test.assertk.AssertMultipleSpec${"$"}BasicObject>
- expected [toString]:<"[wrong]"> but was:<"[BasicObject(arg1=test, arg2=1)]"> (BasicObject(arg1=test, arg2=1))""")
        }
    }

    on("an assert all block") {
        val subject1 = BasicObject("test1", 1)
        val subject2 = BasicObject("test2", 2)

        it("should pass multiple successful assertions") {
            assertAll {
                assert(subject1).toStringFun().isEqualTo("BasicObject(arg1=test1, arg2=1)")
                assert(subject2).toStringFun().isEqualTo("BasicObject(arg1=test2, arg2=2)")
            }
        }

        it("should fail the first assertion") {
            Assertions.assertThatThrownBy {
                assertAll {
                    assert(subject1).toStringFun().isEqualTo("wrong1")
                    assert(subject2).toStringFun().isEqualTo("BasicObject(arg1=test2, arg2=2)")
                }
            }.hasMessage("expected [toString]:<\"[wrong1]\"> but was:<\"[BasicObject(arg1=test1, arg2=1)]\"> (BasicObject(arg1=test1, arg2=1))")
        }

        it("should fail the second assertion") {
            Assertions.assertThatThrownBy {
                assertAll {
                    assert(subject1).toStringFun().isEqualTo("BasicObject(arg1=test1, arg2=1)")
                    assert(subject2).toStringFun().isEqualTo("wrong2")
                }
            }.hasMessage("expected [toString]:<\"[wrong2]\"> but was:<\"[BasicObject(arg1=test2, arg2=2)]\"> (BasicObject(arg1=test2, arg2=2))")
        }

        it("should fail both assertions") {
            Assertions.assertThatThrownBy {
                assertAll {
                    assert(subject1).toStringFun().isEqualTo("wrong1")
                    assert(subject2).toStringFun().isEqualTo("wrong2")
                }
            }.hasMessage("""The following 2 assertions failed:
- expected [toString]:<"[wrong1]"> but was:<"[BasicObject(arg1=test1, arg2=1)]"> (BasicObject(arg1=test1, arg2=1))
- expected [toString]:<"[wrong2]"> but was:<"[BasicObject(arg1=test2, arg2=2)]"> (BasicObject(arg1=test2, arg2=2))""")
        }
    }

}) {
    data class BasicObject(val arg1: String, val arg2: Int)
}


