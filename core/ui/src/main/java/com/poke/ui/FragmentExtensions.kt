package com.poke.ui

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun Fragment.repeatInViewScope(block: suspend CoroutineScope.() -> Unit): Job {
	return viewLifecycleOwner.lifecycleScope.launch {
		block()
	}
}

fun Fragment.getDrawable(@DrawableRes resId: Int) = requireContext().getDrawableCompat(resId)