package br.com.driw.sysgine.common.utils

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class IterableInlineTest : ShouldSpec({
	context("has") {
		val list = listOf(1, 2, 3)

		should("list has item condition") {
			list.has { it == 1 } shouldBe true
			list.has { it in 1..3 } shouldBe true
		}

		should("list hasn't item condition") {
			list.has { it == 0 } shouldBe false
			list.has { it == 4 } shouldBe false
		}
	}
})
