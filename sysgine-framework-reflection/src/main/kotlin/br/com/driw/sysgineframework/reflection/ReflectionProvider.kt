package br.com.driw.sysgineframework.reflection

import org.reflections.Reflections
import org.reflections.scanners.Scanners

object ReflectionProvider {

	private var reflections = Reflections()

	fun reflection() = reflections
	fun reflection(newPackage: String) =
		newPackage.takeIf { it.isEmpty() }
			?.let { Reflections() }
			?: Reflections(newPackage, Scanners.values())
				.also { reflections = it }
}
