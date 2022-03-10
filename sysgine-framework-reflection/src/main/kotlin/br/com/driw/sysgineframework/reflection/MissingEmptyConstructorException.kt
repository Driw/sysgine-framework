package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.SysgineException

class MissingEmptyConstructorException(target: AnyKClass) : SysgineException(
	language = ReflectionMessages.MISSING_EMPTY_CONSTRUCTOR,
	parameters = mapOf(
		"target" to target.qualifiedName
	)
)
