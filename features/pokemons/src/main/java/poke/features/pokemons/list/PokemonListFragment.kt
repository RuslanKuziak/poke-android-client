package poke.features.pokemons.list

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poke.ui.adapter.DividerItemDecoration
import com.poke.ui.getDrawable
import com.poke.ui.repeatInViewScope
import com.poke.ui.view.NoConnectionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import poke.features.pokemons.R
import poke.features.pokemons.list.adapter.LoadStateProgressAdapter
import poke.features.pokemons.list.adapter.PokemonListAdapter
import poke.features.pokemons.list.extension.isNotLoading

@AndroidEntryPoint
class PokemonListFragment : Fragment(R.layout.fragment_pokemons) {

	private val viewModel by viewModels<PokemonListViewModel>()

	private lateinit var adapter: PokemonListAdapter

	private var progress: ProgressBar? = null
	private var connection: NoConnectionView? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		adapter = PokemonListAdapter(layoutInflater, onClick = { viewModel.onItemSelected(it) })

		lifecycleScope.launch { viewModel.event.collectLatest { onEventChanged(it) } }
		lifecycleScope.launch { viewModel.data.collectLatest { adapter.update(it) } }
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		progress = view.findViewById(R.id.progress)
		connection = view.findViewById(R.id.connection)

		val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
		val total = view.findViewById<TextView>(R.id.total_count)

		recyclerView.setHasFixedSize(true)
		recyclerView.layoutManager = LinearLayoutManager(view.context)

		recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
			header = LoadStateProgressAdapter(layoutInflater),
			footer = LoadStateProgressAdapter(layoutInflater)
		)

		recyclerView.addItemDecoration(DividerItemDecoration(getDrawable(R.drawable.list_divider)))

		repeatInViewScope { viewModel.state.collectLatest { onUiStateChanged(it) } }

		adapter.addLoadStateListener { onLoadStateChanged(total, it) }
	}

	override fun onDestroyView() {
		super.onDestroyView()

		progress = null
		connection = null
	}

	private fun onLoadStateChanged(
		total: TextView,
		state: CombinedLoadStates
	) {
		val progress = progress ?: return

		if (state.isNotLoading()) {
			progress.visibility = View.GONE
			updateHeader(total)
		}
	}

	private fun updateHeader(total: TextView) {
		val count = adapter.getTotalItemsCount()
		total.isVisible = count > 0
		total.text = resources.getQuantityString(R.plurals.total_items, count, count)
	}

	private fun onUiStateChanged(state: PokemonListViewModel.UiState) {
		val connection = connection ?: return

		connection.retry { viewModel.retry() }
		connection.isVisible = state.hasConnection.not()
	}

	private fun onEventChanged(event: PokemonListViewModel.Event) {
		when (event) {
			is PokemonListViewModel.Event.OnSelected -> {
				setFragmentResult(
					SELECTED_ID_KEY,
					Bundle().apply { putString(SELECTED_ID_KEY, event.id) })
			}
		}
	}

	companion object {
		const val SELECTED_ID_KEY = "selected_id_key"

		fun newInstance() = PokemonListFragment()
	}
}