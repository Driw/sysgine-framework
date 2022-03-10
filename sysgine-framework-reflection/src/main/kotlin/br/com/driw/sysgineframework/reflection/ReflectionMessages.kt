package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.language.Language

enum class ReflectionMessages(
	override var message: String
) : Language {

	MISSING_IMPLEMENTATION	("implementation not found for the target class"),
	MISSING_EMPTY_CONSTRUCTOR	("implementation not found for the target class"),

	;

	override val code: Int = this.ordinal + 1
}
