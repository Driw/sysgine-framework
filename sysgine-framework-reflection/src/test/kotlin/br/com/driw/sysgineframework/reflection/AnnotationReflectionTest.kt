package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.fixture.FixtureProvider.random
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
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
				TestAnnotation::class.findTypesAnnotated() shouldContainExactlyInAnyOrder listOf(
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

		context("find functions annotated") {
			should("list functions who is annotated by") {
				TestNotAnnotated::class.findFunctionsAnnotated()
					.map { it.name } shouldContainExactlyInAnyOrder listOf("methodNotAnnotated", "companionMethodNotAnnotated")
				TestAnnotation::class.findFunctionsAnnotated()
					.map { it.name } shouldContainExactlyInAnyOrder listOf("methodString", "methodInt")
			}
		}
	}

		context("find properties annotated") {
			should("list properties who is annotated by") {
				TestNotAnnotated::class.findPropertiesAnnotated(TestProperty::class)
					.map { it.name } shouldContainExactlyInAnyOrder listOf("nonRequiredProperty")
				TestAnnotation::class.findPropertiesAnnotated(TestProperty::class)
					.map { it.name } shouldContainExactlyInAnyOrder listOf("nonPrivateRequiredProperty")
				TestNotAnnotated::class.findConstructorPropertiesAnnotated(TestProperty::class)
					.map { it.name } shouldContainExactlyInAnyOrder listOf("requiredProperty")
				TestAnnotation::class.findConstructorPropertiesAnnotated(TestProperty::class)
					.map { it.name } shouldContainExactlyInAnyOrder listOf("requiredPrivateProperty")
				TestNotAnnotated::class.findCompanionPropertiesAnnotated(TestProperty::class)
					.map { it.name } shouldContainExactlyInAnyOrder listOf("companionObjectProperty")
				TestAnnotation::class.findCompanionPropertiesAnnotated(TestProperty::class)
					.map { it.name } shouldContainExactlyInAnyOrder listOf("privateCompanionObjectProperty")
				TestAnnotation::class.findCompanionPropertiesAnnotated(TestNonCompanionObject::class) shouldBe emptyList()
			}
		}
	}

	annotation class TestNotAnnotated
	annotation class TestAnnotation

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
			@TestNotAnnotated fun companionMethodNotAnnotated(): String = random()
		}
	}

	private class TestProperty(
		@TestNotAnnotated val requiredProperty: Boolean = random(),
		@TestAnnotation private val requiredPrivateProperty: String = random()
	) {
		@TestNotAnnotated val nonRequiredProperty: Int = random()
		@TestAnnotation private val nonPrivateRequiredProperty: Double = random()

		companion object {
			@TestNotAnnotated val companionObjectProperty: String = random()
			@TestAnnotation val privateCompanionObjectProperty: String = random()
		}
	}

	private class TestNonCompanionObject
}
