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
				TestInterface::class.findImplementation() shouldBe TestInterfaceImpl::class
			}

			should("when the implementation have priority") {
				TestPriorityInterface::class.findImplementation() shouldBe TestPriorityInterfacePrioritized::class
			}

			should("when the implementation is missing") {
				shouldThrow<MissingImplementationException> { TestInterfaceNotImplemented::class.findImplementation() }
					.should {
						it.language shouldBe ReflectionMessages.MISSING_IMPLEMENTATION
						it.parameters shouldNotBe null
						it.parameters["target"] shouldBe TestInterfaceNotImplemented::class.java.name
					}
			}
		}
	}
}

private interface TestInterface
private interface TestInterfaceExtend : TestInterface
private enum class TestInterfaceEnum : TestInterface
private abstract class TestInterfaceAbstract : TestInterface
private class TestInterfaceImpl : TestInterface

private interface TestPriorityInterface
private class TestPriorityInterfaceNotPrioritized : TestPriorityInterface
@Priority(value = 1)
private class TestPriorityInterfacePrioritized : TestPriorityInterface

private interface TestInterfaceNotImplemented
