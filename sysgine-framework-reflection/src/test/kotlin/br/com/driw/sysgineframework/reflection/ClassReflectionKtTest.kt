package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgineframework.reflection.utils.AnyKClass
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class ClassReflectionKtTest : ShouldSpec({

	beforeSpec { ReflectionProviderSetup.beforeSpec<ClassReflectionKtTest>() }
	afterSpec { ReflectionProviderSetup.afterSpec() }

	context("find any sub type") {
		should("list any extended type from target") {
			TestInterface::class.findAnySubType() shouldContainExactlyInAnyOrder listOf(ConcreteClass::class)
			TestAbstractClass::class.findAnySubType() shouldContainExactlyInAnyOrder listOf(ConcreteClass::class)
			ConcreteClass::class.findAnySubType() shouldBe null
			MultipleRoot::class.findAnySubType() shouldContainExactlyInAnyOrder listOf(LeafOne::class, LeafTwo::class, LeafThree::class)
		}
	}

	context("find leaf implementation") {
		should("get him self by no subtypes and is concrete class") {
			ConcreteClass::class.findLeafImplementation() shouldBe ConcreteClass::class
		}

		should("get leaf implementation") {
			MultipleInterfaceValid::class.findLeafImplementation() shouldBe LeafThree::class
		}

		should("throws not implemented exception") {
			val expected = NotImplementedException(TestUnusedInterface::class)

			shouldThrow<NotImplementedException> {
				TestUnusedInterface::class.findLeafImplementation()
			} shouldBe expected
		}

		should("throws no concrete class implemented exception") {
			val expected = NoConcreteClassImplementedException(TestLeafAbstract::class)

			shouldThrow<NoConcreteClassImplementedException> {
				TestLeafAbstract::class.findLeafImplementation()
			} shouldBe expected
		}

		should("throw multiple leaf implementation exception") {
			val leaves = listOf(LeafTwo::class, LeafThree::class)
			val expected = MultipleLeafImplementationException(MultipleInterfaceInvalid::class, leaves)

			shouldThrow<MultipleLeafImplementationException> {
				MultipleInterfaceInvalid::class.findLeafImplementation()
			} shouldBe expected
		}
	}

	context("validate as concreteClass") {
		should("get it self before validate as concrete class") {
			ConcreteClass::class.validateAsConcreteClass() shouldBe ConcreteClass::class
		}

		should("throws not implemented exception from interface") {
			shouldThrow<NotImplementedException> {
				TestInterface::class.validateAsConcreteClass()
			} shouldBe NotImplementedException(TestInterface::class)
		}

		should("throws no concrete class implementation exception") {
			shouldThrow<NoConcreteClassImplementedException> {
				TestAbstractClass::class.validateAsConcreteClass()
			} shouldBe NoConcreteClassImplementedException(TestAbstractClass::class)
		}
	}

	context("filter leaves classes") {
		should("list the leaf between super and sub type listed") {
			listOf<AnyKClass>(MultipleRoot::class, LeafOne::class).filterLeavesOf() shouldBe listOf(LeafOne::class)
		}

		should("list the both leaf types of same super type") {
			listOf(MultipleRoot::class, LeafOne::class, LeafTwo::class, LeafThree::class)
				.filterLeavesOf() shouldContainExactlyInAnyOrder listOf(LeafTwo::class, LeafThree::class)
		}
	}
}) {
	private interface TestInterface
	private abstract class TestAbstractClass
	private class ConcreteClass : TestAbstractClass(), TestInterface
	private abstract class TestLeafAbstract

	private interface TestUnusedInterface
	private interface MultipleInterfaceInvalid
	private interface MultipleInterfaceValid
	private open class MultipleRoot
	private open class LeafOne : MultipleRoot(), MultipleInterfaceInvalid, MultipleInterfaceValid
	private class LeafTwo : MultipleRoot(), MultipleInterfaceInvalid
	private class LeafThree : LeafOne()
}
