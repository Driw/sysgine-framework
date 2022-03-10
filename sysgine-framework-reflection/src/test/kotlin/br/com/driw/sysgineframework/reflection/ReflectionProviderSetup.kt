package br.com.driw.sysgineframework.reflection

object ReflectionProviderSetup {

	inline fun <reified T> beforeSpec() = ReflectionProvider.reflection(T::class.java.packageName)
	fun afterSpec() = ReflectionProvider.reflection("")
}
