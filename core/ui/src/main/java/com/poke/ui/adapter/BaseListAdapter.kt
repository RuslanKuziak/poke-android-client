package com.poke.ui.adapter

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

interface Identifiable {
	val id: String
}

abstract class BaseListAdapter<I : Identifiable, VH : RecyclerView.ViewHolder>(callback: DiffUtil.ItemCallback<I>) :
	ListAdapter<I, VH>(AsyncDifferConfig.Builder(callback).build())