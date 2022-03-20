package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.utils.has
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.jvm.kotlinFunction

inline fun <reified A : Annotation> KClass<A>.findTypesAnnotated(): AnyKClassList =
	ReflectionProvider.reflection()
		.getTypesAnnotatedWith(this.java)
		.map { it.kotlin }

inline fun <reified A : Annotation> KClass<A>.findInheritancesAnnotated(target: AnyKClass): KClass<*>? =
	takeIf { target.annotations.hasAnnotation<A>() }
		?.let { target }
		?: target.allSuperclasses
			.firstOrNull { it.annotations.hasAnnotation<A>() }

inline fun <reified A : Annotation> KClass<A>.findFunctionsAnnotated(): List<KFunction<*>> =
	ReflectionProvider.reflection()
		.getMethodsAnnotatedWith(this.java)
		.map { it.kotlinFunction!! }

inline fun <reified A : Annotation> List<Annotation>.hasAnnotation(): Boolean =
	this.has { a -> a.annotationClass == A::class }
