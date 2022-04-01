package br.com.driw.sysgineframework.reflection

import br.com.driw.sysgineframework.reflection.utils.isAbstract
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

inline fun <reified T : Any> KClass<T>.findAnySubType(): List<KClass<out T>>? =
	ReflectionProvider.reflection().getSubTypesOf(this.java)
		.takeIf { it.isEmpty().not() }
		?.map { it.kotlin }
		?.toList()

inline fun <reified T : Any> KClass<T>.findLeafImplementation(): KClass<out T> =
	this.findAnySubType()
		?.let { kotlinClasses ->
			takeIf { kotlinClasses.isNotEmpty() }
				?. let {
					val leaves = kotlinClasses.filterLeavesOf()
					leaves.takeIf { it.size == 1 }
						?.first()
						?.validateAsConcreteClass()
						?: throw MultipleLeafImplementationException(this, leaves)
				}
		}
		?: validateAsConcreteClass()

inline fun <reified T : Any> List<KClass<out T>>.filterLeavesOf(): List<KClass<out T>> =
	this.filter { target ->
		this.filter { it != target }
			.map { target.isSuperclassOf(it) }
			.all { !it }
	}

inline fun <reified T : Any> KClass<out T>.validateAsConcreteClass(): KClass<out T> {
	if (this.java.isInterface)
		throw NotImplementedException(this)

	if (this.java.isAbstract)
		throw NoConcreteClassImplementedException(this)

	return this
}
