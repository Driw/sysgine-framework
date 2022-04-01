package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.SysgineException
import br.com.driw.sysgineframework.reflection.utils.AnyKClass

class NotImplementedException(target: AnyKClass) : SysgineException(
	language = ReflectionMessages.NOT_IMPLEMENTED,
	parameters = mapOf(
		"target" to target.qualifiedName
	)
)
