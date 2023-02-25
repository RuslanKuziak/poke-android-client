package poke.features.pokemons.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import poke.features.pokemons.R
import poke.features.pokemons.list.PokemonItemModel

class PokemonListAdapter(
	private val inflater: LayoutInflater,
	private val onClick: (id: String) -> Unit
) : PagingDataAdapter<PokemonItemModel, PokemonListAdapter.ViewHolder>(COMPARATOR) {

	companion object {
		private val COMPARATOR = object : DiffUtil.ItemCallback<PokemonItemModel>() {
			override fun areItemsTheSame(
				oldItem: PokemonItemModel,
				newItem: PokemonItemModel
			): Boolean {
				return oldItem.name == newItem.name
			}

			override fun areContentsTheSame(
				oldItem: PokemonItemModel,
				newItem: PokemonItemModel
			): Boolean {
				return oldItem == newItem
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(inflater.inflate(R.layout.item_pokemon, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.onBind(getItem(position) ?: return)
	}

	suspend fun update(data: PagingData<PokemonItemModel>) {
		submitData(data)
	}

	fun getTotalItemsCount(): Int {
		return if (itemCount > 0) getItem(0)?.total ?: 0 else 0
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		private val textView: TextView = itemView.findViewById(R.id.text_view)

		init {
			itemView.setOnClickListener {
				onClick(getItem(bindingAdapterPosition)?.name ?: return@setOnClickListener)
			}
		}

		fun onBind(item: PokemonItemModel) {
			textView.text = formattedName(original = item.name)
		}

		private fun formattedName(original: String): CharSequence {
			return original.replaceFirstChar { it.uppercase() }
		}
	}

}