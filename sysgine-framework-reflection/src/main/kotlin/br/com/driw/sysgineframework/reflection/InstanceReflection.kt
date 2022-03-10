package br.com.driw.sysgineframework.reflection

import kotlin.reflect.KClass

inline fun <reified T : Any> KClass<T>.buildInstance(): T =
	runCatching { this.java.getConstructor() }
		.onFailure { throw MissingEmptyConstructorException(this) }
		.getOrNull()!!
		.newInstance()
