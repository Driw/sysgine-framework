package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.SysgineException
import br.com.driw.sysgineframework.reflection.utils.AnyKClass
import br.com.driw.sysgineframework.reflection.utils.AnyKClassList

class MultipleLeafImplementationException(target: AnyKClass, leaves: AnyKClassList) : SysgineException(
	language = ReflectionMessages.MULTIPLE_LEAF_IMPLEMENTATION,
	parameters = mapOf(
		"target" to target.qualifiedName,
		"leaves" to leaves.map { it.qualifiedName }.joinToString(),
	)
)
