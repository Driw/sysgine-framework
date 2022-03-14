package br.com.driw.sysgineframework.reflection

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class AnnotationReflectionTest : ShouldSpec() {
	init {

		beforeSpec { ReflectionProviderSetup.beforeSpec<AnnotationReflectionTest>() }
		afterSpec { ReflectionProviderSetup.afterSpec() }

		context("find types annotated") {
			should("list any who is annotated or has a extension or implementation who has it") {
				TestNotAnnotated::class.findTypesAnnotated() shouldBe emptyList()
				TestAnnotation::class.findTypesAnnotated() shouldBe listOf(
					ConcreteTest::class,
					InterfaceTest::class,
					ExtendedClassTest::class,
					ExtendedInterfaceTest::class
				)
			}
		}

		context("find inheritances annotated") {
			should("list exactly who is annotated") {
				TestNotAnnotated::class.findInheritancesAnnotated(ConcreteTest::class) shouldBe null
				TestAnnotation::class.findInheritancesAnnotated(ConcreteTest::class) shouldBe ConcreteTest::class
				TestAnnotation::class.findInheritancesAnnotated(ExtendedClassTest::class) shouldBe ConcreteTest::class
				TestAnnotation::class.findInheritancesAnnotated(InterfaceTest::class) shouldBe InterfaceTest::class
				TestAnnotation::class.findInheritancesAnnotated(ExtendedInterfaceTest::class) shouldBe InterfaceTest::class
			}
		}
	}
}

private annotation class TestNotAnnotated
private annotation class TestAnnotation

@TestAnnotation
private open class ConcreteTest
@TestAnnotation
private interface InterfaceTest
private class ExtendedClassTest : ConcreteTest()
private interface ExtendedInterfaceTest : InterfaceTest
