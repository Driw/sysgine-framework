package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.fixture.FixtureProvider.random
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class AnnotationReflectionTest : ShouldSpec() {

	init {

		beforeSpec { ReflectionProviderSetup.beforeSpec<AnnotationReflectionTest>() }
		afterSpec { ReflectionProviderSetup.afterSpec() }

		context("find types annotated") {
			should("list nothing because anything is annotated by") {
				TestNotAnnotated::class.findTypesAnnotated() shouldBe emptyList()
			}

			should("list any who is annotated or has a extension or implementation who has it") {
				TestAnnotation::class.findTypesAnnotated() shouldContainAll listOf(
					ConcreteTest::class,
					AnnotatedInterfaceTest::class,
					ExtendedClassTest::class,
					ExtendedAnnotatedInterfaceTest::class
				)
			}
		}

		context("find inheritances annotated") {
			should("get nul because anyone is annotated with") {
				TestNotAnnotated::class.findInheritancesAnnotated(ConcreteTest::class) shouldBe null
			}

			should("get who exactly is annotated") {
				TestAnnotation::class.findInheritancesAnnotated(ConcreteTest::class) shouldBe ConcreteTest::class
				TestAnnotation::class.findInheritancesAnnotated(ExtendedClassTest::class) shouldBe ConcreteTest::class
				TestAnnotation::class.findInheritancesAnnotated(AnnotatedInterfaceTest::class) shouldBe AnnotatedInterfaceTest::class
				TestAnnotation::class.findInheritancesAnnotated(ExtendedAnnotatedInterfaceTest::class) shouldBe AnnotatedInterfaceTest::class
			}
		}

		context("find methods annotated") {
			should("list functions who is annotated by") {
				TestNotAnnotated::class.findFunctionsAnnotated()
					.map { it.name } shouldContainAll listOf("methodNotAnnotated", "staticMethodNotAnnotated")
				TestAnnotation::class.findFunctionsAnnotated()
					.map { it.name } shouldContainAll listOf("methodString", "methodInt")
			}
		}
	}

	private annotation class TestNotAnnotated
	private annotation class TestAnnotation

	@TestAnnotation
	private interface AnnotatedInterfaceTest
	private interface ExtendedAnnotatedInterfaceTest : AnnotatedInterfaceTest

	@TestAnnotation
	private open class ConcreteTest
	private open class ExtendedClassTest : ConcreteTest()

	private class TestMethods {
		fun methodWithoutAnnotation(): Boolean = random()
		@TestNotAnnotated fun methodNotAnnotated(): String = random()
		@TestAnnotation fun methodString(): String = random()
		@TestAnnotation fun methodInt(): Int = random()

		companion object {
			@TestNotAnnotated fun staticMethodNotAnnotated(): String = random()
		}
	}
}
