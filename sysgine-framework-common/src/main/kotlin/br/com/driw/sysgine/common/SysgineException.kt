package br.com.driw.sysgine.common

import br.com.driw.sysgine.common.language.Language
import java.io.PrintWriter

open class SysgineException(
	val language: Language,
	val parameters: Map<String, Any?> = emptyMap(),
	override val cause: Throwable? = null
) : Exception(language.message, cause) {

	val formattedMessage: String = SysgineExceptionUtils.formatLanguageMessage(language, parameters)
	val code: Int = this.language.code

	override fun printStackTrace(writer: PrintWriter) {
		writer.println(SysgineExceptionUtils.formatParameters(parameters))

		super.printStackTrace(writer)
	}
}
