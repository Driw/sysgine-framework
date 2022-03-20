package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgine.common.utils.has
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.memberProperties
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

inline fun <reified A : Annotation> KClass<A>.findPropertiesAnnotated(target: AnyKClass): List<KProperty<*>> =
	target.declaredMemberProperties
		.filter { declaredProperty -> declaredProperty.annotations.hasAnnotation<A>() }

inline fun <reified A : Annotation> KClass<A>.findConstructorPropertiesAnnotated(target: AnyKClass): List<KParameter> =
	target.constructors.first()
		.parameters
		.filter { declaredProperty -> declaredProperty.annotations.hasAnnotation<A>() }

inline fun <reified A : Annotation> KClass<A>.findCompanionPropertiesAnnotated(target: AnyKClass): List<KProperty1<out Any, *>> =
	target.companionObject?.let {
		it.memberProperties
			.filter { declaredProperty -> declaredProperty.annotations.hasAnnotation<A>() }
	} ?: emptyList()

inline fun <reified A : Annotation> KClass<A>.findFunctionsAnnotated(): List<KFunction<*>> =
	ReflectionProvider.reflection()
		.getMethodsAnnotatedWith(this.java)
		.map { it.kotlinFunction!! }

inline fun <reified A : Annotation> KClass<A>.findFunctionsAnnotated(target: AnyKClass): List<KFunction<*>> =
	target.declaredFunctions
		.filter { declaredFunction -> declaredFunction.hasAnnotation<A>() }

inline fun <reified A : Annotation> KClass<A>.findCompanionFunctionsAnnotated(target: AnyKClass): List<KFunction<*>> =
	target.companionObject?.let {
		it.declaredFunctions
			.filter { declaredFunction -> declaredFunction.hasAnnotation<A>() }
	} ?: emptyList()

inline fun <reified A : Annotation> List<Annotation>.hasAnnotation(): Boolean =
	this.has { a -> a.annotationClass == A::class }
