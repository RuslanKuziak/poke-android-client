package poke.features.pokemondetails.utils

import android.graphics.drawable.GradientDrawable

internal fun obtainBackgroundDrawable(radius: Float) = GradientDrawable().apply {
	cornerRadius = radius
	setColor(obtainRandomColor())
}