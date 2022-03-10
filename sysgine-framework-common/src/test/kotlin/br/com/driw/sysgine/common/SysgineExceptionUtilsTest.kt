package br.com.driw.sysgine.common

import br.com.driw.sysgine.common.language.Language
import br.com.driw.sysgine.fixture.FixtureProvider.random
import br.com.driw.sysgine.fixture.FixtureProvider.randomMap
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class SysgineExceptionUtilsTest : ShouldSpec({
	context("format language message") {
		should("return the message it self because parameters is empty") {
			val formattedLanguageMessage = random<String>()
			val language: Language = mockk {
				every { message } returns formattedLanguageMessage
			}
			val parameters: Map<String, Any> = emptyMap()

			SysgineExceptionUtils.formatLanguageMessage(language, parameters) shouldBe formattedLanguageMessage

			verify(exactly = 1) { language.message }
		}

		should("return the message with parameters formatted") {
			val languageMessage = random<String>()
			val language: Language = mockk {
				every { message } returns languageMessage
			}
			val parameters = randomMap<String, String>(size = 2)

			SysgineExceptionUtils.formatLanguageMessage(language, parameters)
				.should {
					val listParameter = parameters.entries.toList()
					val first = listParameter[0]
					val second = listParameter[1]

					it shouldBe "$languageMessage (${first.key}: ${first.value}, ${second.key}: ${second.value})"
				}

			verify(exactly = 1) { language.message }
		}

		should("return parameters formatted") {
			val parameters = randomMap<String, String>(size = 2)

			SysgineExceptionUtils.formatParameters(parameters)
				.should {
					val listParameter = parameters.entries.toList()
					val first = listParameter[0]
					val second = listParameter[1]

					it shouldBe "${first.key}: ${first.value}, ${second.key}: ${second.value}"
				}
		}
	}
})
