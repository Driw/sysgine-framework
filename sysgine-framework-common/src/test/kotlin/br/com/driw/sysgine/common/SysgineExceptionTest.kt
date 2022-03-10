package br.com.driw.sysgine.common

import br.com.driw.sysgine.common.language.Language
import br.com.driw.sysgine.fixture.FixtureProvider.random
import br.com.driw.sysgine.fixture.FixtureProvider.randomMap
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.PrintWriter

class SysgineExceptionTest : ShouldSpec({

	beforeSpec { mockkObject(SysgineExceptionUtils) }
	afterEach { clearAllMocks() }

	context("get code") {
		should("return language code") {
			val formattedMessage = random<String>()
			val parameters = randomMap<String, Any>(size = 2)
			val language: Language = mockk {
				every { code } returns random()
				every { this@mockk.message } returns random()
			}
			every { SysgineExceptionUtils.formatLanguageMessage(language, parameters) } returns formattedMessage

			SysgineException(language, parameters)
				.should {
					it.code shouldBe language.code
					it.message shouldBe language.message
					it.parameters shouldBe parameters
					it.formattedMessage shouldBe formattedMessage
				}

			verify(exactly = 1) { SysgineExceptionUtils.formatLanguageMessage(language, parameters) }
		}
	}

	context("print stack trace") {
		should("print exception parameters then native stack trace") {
			val language = mockk<Language> {
				every { message } returns random()
				every { code } returns random()
			}
			val stream = ByteArrayOutputStream()
			val writer = PrintWriter(stream)
			val exception = SysgineException(language = language)
			val formattedMessage = random<String>()

			every { SysgineExceptionUtils.formatParameters(exception.parameters) } returns formattedMessage

			exception.printStackTrace(writer)

			InputStreamReader(ByteArrayInputStream(stream.toByteArray())).readLines()
				.should {
					it.size shouldBeGreaterThan 3
					it[0] shouldBe formattedMessage
					it[1] shouldBe "${SysgineException::class.java.name}: ${language.message}"
					it[2].startsWith("\tat ${SysgineExceptionTest::class.java.name}") shouldBe true
				}

			verify(exactly = 1) { SysgineExceptionUtils.formatParameters(exception.parameters) }
		}
	}
})
