package com.poke.ui

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ImageLoader private constructor(
	@DrawableRes private var placeholderResId: Int = 0,
	@DrawableRes private var drawableResId: Int = 0,
	private var target: ImageView,
	private var string: String = "",
	private var crossFade: Boolean = false,
	private var cornerRadius: Int = 0
) {

	init {
		load()
	}

	private fun load() {
		val manager = Glide.with(target.context)

		var builder = when {
			drawableResId != 0 -> {
				manager.load(drawableResId)
			}
			else -> {
				manager.load(string)
			}
		}

		if (crossFade) {
			builder = builder.transition(DrawableTransitionOptions.withCrossFade())
		}

		if (placeholderResId != 0) {
			builder = builder.placeholder(placeholderResId)
		}

		if (cornerRadius > 0) {
			builder = builder.apply(buildRequestOptions())
		}

		builder.into(target)
	}

	private fun buildRequestOptions(): RequestOptions {
		return RequestOptions().transform(RoundedCorners(cornerRadius))
	}

	@Suppress("unused")
	data class LoadParams(
		@DrawableRes private var placeholderResId: Int = 0,
		@DrawableRes private var drawableResId: Int = 0,
		private var image: ImageView? = null,
		private var string: String = "",
		private var crossFade: Boolean = false,
		private var cornerRadius: Int = 0
	) {

		fun from(@DrawableRes resId: Int) = apply {
			drawableResId = resId
		}

		fun from(value: String) = apply {
			string = value
		}

		fun withPlaceholder(@DrawableRes resId: Int) = apply {
			placeholderResId = resId
		}

		fun withCrossFade() = apply {
			crossFade = true
		}

		fun withRoundedCorners(value: Int) = apply {
			cornerRadius = value
		}

		fun loadInto(target: ImageView): ImageLoader {
			image = target

			return ImageLoader(
				placeholderResId,
				drawableResId,
				target,
				string,
				crossFade,
				cornerRadius
			)
		}
	}
}