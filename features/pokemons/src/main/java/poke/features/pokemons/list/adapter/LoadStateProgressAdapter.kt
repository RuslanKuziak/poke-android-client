package poke.features.pokemons.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.poke.ui.adapter.LoadingViewHolder
import poke.features.pokemons.R


class LoadStateProgressAdapter(
	private val inflater: LayoutInflater
) : LoadStateAdapter<LoadStateProgressAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
		return ViewHolder(inflater.inflate(R.layout.item_loading, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
		holder.onBind(loadState)
	}

	inner class ViewHolder(view: View) : LoadingViewHolder(view) {

		private val progressBar = view.findViewById<ProgressBar>(R.id.progress)

		fun onBind(state: LoadState) {
			progressBar.isVisible = state is LoadState.Loading
		}
	}
}
