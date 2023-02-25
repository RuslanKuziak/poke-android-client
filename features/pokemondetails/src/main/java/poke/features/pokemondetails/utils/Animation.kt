package poke.features.pokemondetails.utils

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnStart

fun shakeAnimate(target: View, duration: Long = 1000L, doOnStart: () -> Unit) {
	val animator = ObjectAnimator
		.ofFloat(target, View.TRANSLATION_X, 0f, -25f, 25f, -15f, 15f, -5f, 5f, 0f)
		.setDuration(duration)

	animator.doOnStart { doOnStart() }
	animator.start()
}