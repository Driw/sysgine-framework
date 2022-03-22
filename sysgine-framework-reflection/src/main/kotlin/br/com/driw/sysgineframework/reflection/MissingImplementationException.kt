package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.SysgineException
import br.com.driw.sysgineframework.reflection.utils.AnyKClass

class MissingImplementationException(
	target: AnyKClass
) : SysgineException(
	language = ReflectionMessages.MISSING_IMPLEMENTATION,
	parameters = mapOf(
		"target" to target.qualifiedName,
	)
)
