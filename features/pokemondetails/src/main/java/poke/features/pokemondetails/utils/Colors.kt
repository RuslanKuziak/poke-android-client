package poke.features.pokemondetails.utils

import android.graphics.Color
import java.util.*

internal fun obtainRandomColor(): Int {
	val random = Random()
	val a = 255
	val r = random.nextInt(256)
	val g = random.nextInt(256)
	val b = random.nextInt(256)
	return Color.argb(a, r, g, b)
}
