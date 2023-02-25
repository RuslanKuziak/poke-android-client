package poke.features.pokemondetails

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.poke.domain.model.PokemonForm
import com.poke.domain.model.TypeSlot
import com.poke.ui.ImageLoader
import com.poke.ui.repeatInViewScope
import com.poke.ui.view.NoConnectionView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import poke.features.pokemondetails.adapter.GridItemDecoration
import poke.features.pokemondetails.adapter.SpecialTypesAdapter
import poke.features.pokemondetails.utils.obtainBackgroundDrawable
import poke.features.pokemondetails.utils.shakeAnimate

@AndroidEntryPoint
class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

	private val vibrator: Vibrator by lazy { requireActivity().getSystemService(Vibrator::class.java) }
	private val viewModel by viewModels<PokemonDetailsViewModel>()

	private lateinit var adapter: SpecialTypesAdapter

	private var progress: ProgressBar? = null
	private var title: TextView? = null
	private var connection: NoConnectionView? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		adapter = SpecialTypesAdapter(layoutInflater)

		lifecycleScope.launch { viewModel.event.collectLatest { onEventChanged(it) } }
		viewModel.init(requireArguments().getString(ID_KEY, ""))
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		progress = view.findViewById(R.id.progress)
		title = view.findViewById(R.id.title)
		connection = view.findViewById(R.id.connection)

		repeatInViewScope { viewModel.state.collectLatest { onUiStateChanged(it) } }

		repeatInViewScope {
			viewModel.result
				.filter { it.isValid }
				.collectLatest { onResultReceived(it) }
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()

		progress = null
		title = null
	}

	private fun onResultReceived(result: PokemonForm) {
		val title = title ?: return
		title.text = result.name.replaceFirstChar { it.uppercase() }

		val image = requireView().findViewById<ImageView>(R.id.image)
		image.background = obtainBackgroundDrawable(radius = 16F)

		image.setOnLongClickListener {
			shakeAnimate(target = it, doOnStart = { vibrateCompat() })
			return@setOnLongClickListener true
		}

		setupRecyclerView(result.types)

		ImageLoader.LoadParams()
			.from(result.sprites.frontDefaultImage)
			.withCrossFade()
			.loadInto(image)
	}

	private fun setupRecyclerView(items: List<TypeSlot>) {
		val recycler = requireView().findViewById<RecyclerView>(R.id.recycler_view)
		val spans = if (items.size == 1) 1 else 2

		recycler.setHasFixedSize(true)
		recycler.layoutManager = GridLayoutManager(recycler.context, spans)
		recycler.adapter = adapter
		recycler.addItemDecoration(GridItemDecoration())

		adapter.update(items)
	}

	private fun onUiStateChanged(state: PokemonDetailsViewModel.UiState) {
		val progress = progress ?: return
		val title = title ?: return
		val connection = connection ?: return
		connection.retry { viewModel.retry() }

		progress.isVisible = state.isLoading
		title.isVisible = state.isLoading.not()
		connection.isVisible = state.hasConnection.not()
	}

	private fun onEventChanged(event: PokemonDetailsViewModel.Event) {
		when (event) {
			PokemonDetailsViewModel.Event.OnNavigateBack -> parentFragmentManager.popBackStack()
		}
	}

	private fun vibrateCompat() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
			return
		}

		val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)

		vibrator.cancel()
		vibrator.vibrate(effect)
	}

	companion object {
		private const val ID_KEY = "pokemon_id"

		fun newInstance(id: String) = PokemonDetailsFragment().apply {
			arguments = Bundle().apply { putString(ID_KEY, id) }
		}
	}
}