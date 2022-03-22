package br.com.driw.sysgineframework.reflection

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class InterfaceReflectionTest : ShouldSpec() {

	init {

		beforeSpec { ReflectionProviderSetup.beforeSpec<InterfaceReflectionTest>() }
		afterSpec { ReflectionProviderSetup.afterSpec() }

		context("find implementation") {
			should("when the implementation doesn't have priority") {
				InterfaceTest::class.findImplementation() shouldBe InterfaceTestImpl::class
			}

			should("when the implementation have priority") {
				PriorityInterfaceTest::class.findImplementation() shouldBe PriorityInterfaceTestPrioritized::class
			}

			should("when the implementation is missing") {
				val expected = MissingImplementationException(InterfaceNotImplementedTest::class)

				shouldThrow<MissingImplementationException> { InterfaceNotImplementedTest::class.findImplementation() } shouldBe expected
			}
		}
	}

	private interface InterfaceTest
	private interface InterfaceTestExtend : InterfaceTest
	private enum class InterfaceTestEnum : InterfaceTest
	private abstract class InterfaceTestAbstract : InterfaceTest
	private class InterfaceTestImpl : InterfaceTest

	private interface PriorityInterfaceTest
	private class PriorityInterfaceTestNotPrioritized : PriorityInterfaceTest
	@Priority(value = 1)
	private class PriorityInterfaceTestPrioritized : PriorityInterfaceTest

	private interface InterfaceNotImplementedTest
}
