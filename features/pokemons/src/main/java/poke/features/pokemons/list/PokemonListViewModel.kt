package poke.features.pokemons.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.poke.domain.request.GetListRequest
import com.poke.domain.usecase.GetPokemonsPagedUseCase
import com.poke.system.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 25

@HiltViewModel
class PokemonListViewModel @Inject constructor(
	private val networkManager: NetworkManager,
	private val getPokemonsPagedUseCase: GetPokemonsPagedUseCase
) : ViewModel() {

	private val _state = MutableStateFlow(UiState())
	val state = _state.asStateFlow()

	private val _event = MutableSharedFlow<Event>()
	val event = _event.asSharedFlow()

	private val _data = MutableStateFlow<PagingData<PokemonItemModel>>(PagingData.empty())
	val data = _data.asStateFlow()

	init {
		fetchPokemonsIfConnected()
	}

	fun retry() {
		fetchPokemonsIfConnected()
	}

	fun onItemSelected(id: String) {
		if (id.isEmpty()) {
			return
		}

		viewModelScope.launch { _event.emit(Event.OnSelected(id)) }
	}

	private fun fetchPokemonsIfConnected() {
		if (verifyConnection()) {
			executeGetPokemons()
		}
	}

	private fun verifyConnection(): Boolean {
		if (networkManager.hasActiveConnection().not()) {
			_state.update { it.copy(hasConnection = false) }
			return false
		}

		_state.update { it.copy(hasConnection = true) }
		return true
	}

	private fun executeGetPokemons() = viewModelScope.launch {
		getPokemonsPagedUseCase.execute(request = GetListRequest(pageSize = PAGE_SIZE))
			.cachedIn(this)
			.map { it.map { item -> item.toModel() } }
			.collectLatest {
				_data.value = it
			}
	}

	data class UiState(
		val hasConnection: Boolean = true
	)

	sealed interface Event {
		data class OnSelected(val id: String) : Event
	}

}