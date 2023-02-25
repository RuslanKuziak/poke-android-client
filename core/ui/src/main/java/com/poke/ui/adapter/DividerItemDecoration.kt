package com.poke.ui.adapter

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(private val drawable: Drawable) : RecyclerView.ItemDecoration() {

	override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
		super.onDrawOver(canvas, parent, state)

		val dividerLeft: Int = parent.paddingLeft
		val dividerRight: Int = parent.width - parent.paddingRight

		val childCount: Int = parent.childCount
		for (i in 0 until childCount) {
			val viewHolder = parent.findContainingViewHolder(parent.getChildAt(i)) ?: return

			if (viewHolder is LoadingViewHolder) {
				return
			}

			val child = parent.getChildAt(i)
			val params = child.layoutParams as RecyclerView.LayoutParams
			val dividerTop = child.bottom + params.bottomMargin
			val dividerBottom = dividerTop + drawable.intrinsicHeight

			drawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
			drawable.draw(canvas)
		}
	}

}