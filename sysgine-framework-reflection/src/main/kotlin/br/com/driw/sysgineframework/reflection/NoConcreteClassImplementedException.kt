package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.SysgineException
import br.com.driw.sysgineframework.reflection.utils.AnyKClass

class NoConcreteClassImplementedException(target: AnyKClass) : SysgineException(
	language = ReflectionMessages.NO_CONCRETE_IMPLEMENTATION,
	parameters = mapOf(
		"target" to target.qualifiedName
	)
)
