package br.com.driw.sysgineframework.reflection

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

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
				shouldThrow<MissingImplementationException> { InterfaceNotImplementedTest::class.findImplementation() }
					.should {
						it.language shouldBe ReflectionMessages.MISSING_IMPLEMENTATION
						it.parameters shouldNotBe null
						it.parameters["target"] shouldBe InterfaceNotImplementedTest::class.java.name
					}
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
