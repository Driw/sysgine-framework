package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.language.Language

enum class ReflectionMessages(
	override var message: String
) : Language {

	MISSING_IMPLEMENTATION			("implementation not found for the target class"),
	MISSING_EMPTY_CONSTRUCTOR		("required empty constructor to make a new instance but it's was not found it"),
	MULTIPLE_LEAF_IMPLEMENTATION	("found more then one valid implementation to target class"),
	NOT_IMPLEMENTED					("not found any implementation for that interface"),
	NO_CONCRETE_IMPLEMENTATION		("not found any concrete implementation for that type"),


	;

	override val code: Int = this.ordinal + 1
}
