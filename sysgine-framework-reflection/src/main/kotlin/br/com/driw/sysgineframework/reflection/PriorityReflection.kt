package br.com.driw.sysgineframework.reflection

object PriorityReflection {

	inline fun <reified T> findValue(): Int = findValueByClass(T::class.java)

	inline fun <reified T> findValueByClass(it: Class<out T>): Int = it.declaredAnnotations
		.filterIsInstance<Priority>()
		.firstOrNull()
		?.value
		?: 0
}
