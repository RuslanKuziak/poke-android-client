package poke.features.pokemondetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.poke.domain.model.TypeSlot
import com.poke.ui.adapter.BaseListAdapter
import com.poke.ui.adapter.Identifiable
import poke.features.pokemondetails.R
import poke.features.pokemondetails.utils.obtainBackgroundDrawable

private const val CORNER_RADIUS = 40f

class SpecialTypesAdapter(
	private val inflater: LayoutInflater
) : BaseListAdapter<SpecialTypesAdapter.Item, SpecialTypesAdapter.ViewHolder>(DiffCallback()) {

	private class DiffCallback : DiffUtil.ItemCallback<Item>() {
		override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
		override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(inflater.inflate(R.layout.item_special_type, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.onBind(currentList[position])
	}

	fun update(list: List<TypeSlot>) {
		val items = list.map {
			Item(
				id = it.slot.toString(),
				name = it.type.name
			)
		}

		submitList(items)
	}

	data class Item(override val id: String, val name: String) : Identifiable

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		private val textView = view.findViewById<TextView>(R.id.text)

		fun onBind(item: Item) {
			textView.background = obtainBackgroundDrawable(radius = CORNER_RADIUS)
			textView.text = item.name
		}
	}
}