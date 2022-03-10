package br.com.driw.sysgineframework.reflection

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class PriorityReflectionTest : ShouldSpec() {
	init {

		context("find value") {
			context("when isn't annotated") {
				PriorityReflection.findValue<TestA>() shouldBe TEST_A_PRIORITY
			}

			context("when is annotated") {
				PriorityReflection.findValue<TestB>() shouldBe TEST_B_PRIORITY
			}
		}

		context("find value by class") {
			context("when isn't annotated") {
				PriorityReflection.findValueByClass(TestA::class.java) shouldBe TEST_A_PRIORITY
			}

			context("when is annotated") {
				PriorityReflection.findValueByClass(TestB::class.java) shouldBe TEST_B_PRIORITY
			}
		}
	}
}

private const val TEST_A_PRIORITY = 0
private const val TEST_B_PRIORITY = 1

private class TestA

@Priority(value = TEST_B_PRIORITY)
private class TestB
