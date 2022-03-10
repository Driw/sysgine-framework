package br.com.driw.sysgineframework.reflection

import java.lang.reflect.Modifier
import kotlin.reflect.KClass

val <T> Class<T>.isClassType: Boolean get() = !this.isInterface && !this.isEnum
val <T> Class<T>.isAbstract: Boolean get() = Modifier.isAbstract(this.modifiers)
val <T> Class<T>.isConcreteClass: Boolean get() = !this.isAbstract && this.isClassType

typealias AnyKClass = KClass<out Any>
typealias AnyKClassList = List<AnyKClass>
