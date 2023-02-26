package poke.core.data.utils

fun getResourceAsString(path: String): String {
	return ClassLoader.getSystemResource(path).readText()
}