package br.com.driw.sysgine.fixture

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import kotlin.collections.associateWith
import kotlin.collections.toMap
import kotlin.jvm.java

object FixtureProvider {

	private val parameters = EasyRandomParameters()
		.scanClasspathForConcreteTypes(true)
		.collectionSizeRange(1, 10)
	val easyRandom = EasyRandom(parameters)

	inline fun <reified T> random(): T = easyRandom.nextObject(T::class.java)
	inline fun <reified T> randomList(size: Int = 3): List<T> = easyRandom.objects(T::class.java, size).toList()
	inline fun <reified K, reified V> randomMap(size: Int = 3): Map<K, V> = easyRandom.objects(K::class.java, size).toList()
		.associateWith { random<V>() }
		.toMap()
}
