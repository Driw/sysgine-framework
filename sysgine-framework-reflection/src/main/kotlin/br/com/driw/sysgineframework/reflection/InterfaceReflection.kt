package br.com.driw.sysgineframework.reflection

import kotlin.reflect.KClass

inline fun <reified T : Any> KClass<T>.findImplementation(): KClass<out T> =
	ReflectionProvider.reflection()
		.getSubTypesOf(this.java)
		.filter { it.isConcreteClass }
		.maxByOrNull { PriorityReflection.findValueByClass(it) }
		?.kotlin
		?: throw MissingImplementationException(this.java)
