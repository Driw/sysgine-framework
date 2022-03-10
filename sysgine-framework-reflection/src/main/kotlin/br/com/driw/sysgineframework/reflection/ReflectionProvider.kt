package br.com.driw.sysgineframework.reflection

import org.reflections.Reflections
import java.util.Objects

object ReflectionProvider {

	private var reflections = Reflections()

	fun reflection() = reflections
	fun reflection(newPackage: String) =
		(if (Objects.isNull(newPackage) || newPackage.isEmpty()) Reflections() else Reflections(newPackage))
			.also { reflections = it }
}
