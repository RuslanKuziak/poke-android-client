package com.poke.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import poke.core.ui.R

class NoConnectionView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

	private val retry: TextView

	init {
		inflate(context, R.layout.view_no_connection, this)

		retry = findViewById(R.id.retry)
	}

	fun retry(block: () -> Unit) {
		retry.setOnClickListener { block() }
	}

}