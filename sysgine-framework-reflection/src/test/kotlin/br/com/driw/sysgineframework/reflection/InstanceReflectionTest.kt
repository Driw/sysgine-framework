package br.com.driw.sysgineframework.reflection

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.beInstanceOf

class InstanceReflectionTest : ShouldSpec() {

	init {
		beforeSpec { ReflectionProviderSetup.beforeSpec<InstanceReflectionTest>() }
		afterSpec { ReflectionProviderSetup.afterSpec() }

		context("build instance") {
			should("when have empty constructor") {
				EmptyConstructorTest::class.buildInstance() shouldBe beInstanceOf<EmptyConstructorTest>()
			}

			should("when throws by missing empty constructor") {
				shouldThrow<MissingEmptyConstructorException> { NoEmptyConstructorTest::class.buildInstance() }
			}
		}
	}

	private class EmptyConstructorTest
	private class NoEmptyConstructorTest(val arg: Any)
}
