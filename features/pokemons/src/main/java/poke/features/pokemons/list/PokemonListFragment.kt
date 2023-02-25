package poke.features.pokemons.list

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poke.ui.getDrawable
import com.poke.ui.repeatInViewScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import poke.features.pokemons.R
import com.poke.ui.adapter.DividerItemDecoration
import poke.features.pokemons.list.adapter.LoadStateProgressAdapter
import poke.features.pokemons.list.adapter.PokemonListAdapter
import poke.features.pokemons.list.extension.isNotLoading

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_pokemons) {

	private val viewModel by viewModels<PokemonListViewModel>()

	private lateinit var adapter: PokemonListAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		adapter = PokemonListAdapter(layoutInflater, onClick = { viewModel.onItemSelected(it) })
		lifecycleScope.launch { viewModel.data.collectLatest { adapter.update(it) } }
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
		val progress = view.findViewById<ProgressBar>(R.id.progress)
		val total = view.findViewById<TextView>(R.id.total_count)

		recyclerView.setHasFixedSize(true)
		recyclerView.layoutManager = LinearLayoutManager(view.context)

		recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
			header = LoadStateProgressAdapter(layoutInflater),
			footer = LoadStateProgressAdapter(layoutInflater)
		)

		recyclerView.addItemDecoration(DividerItemDecoration(getDrawable(R.drawable.list_divider)))

		adapter.addLoadStateListener {
			repeatInViewScope { onLoadStateChanged(progress, total, it) }
		}
	}

	private fun onLoadStateChanged(
		progressBar: ProgressBar,
		total: TextView,
		state: CombinedLoadStates
	) {
		if (state.isNotLoading()) {
			progressBar.visibility = View.GONE
			updateHeader(total)
		}


	}

	private fun updateHeader(total: TextView) {
		val count = adapter.getTotalItemsCount()
		total.isVisible = count > 0
		total.text = resources.getQuantityString(R.plurals.total_items, count, count)
	}

	companion object {
		fun newInstance() = PokemonListFragment()
	}
}