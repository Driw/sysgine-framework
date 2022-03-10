package br.com.driw.sysgine.common

import br.com.driw.sysgine.common.language.Language
import java.util.Objects

object SysgineExceptionUtils {

	fun formatLanguageMessage(language: Language, parameters: Map<String, *>): String =
		if (parameters.isEmpty()) language.message else "${language.message} (${formatParameters(parameters)})"

	fun formatParameters(parameters: Map<String, *>): String =
		parameters.entries.joinToString(separator = ", ") { "${it.key}: ${Objects.toString(it.value)}" }
}
