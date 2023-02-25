package poke.features.pokemondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poke.domain.model.PokemonForm
import com.poke.domain.usecase.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
	private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
) : ViewModel() {

	private val _state = MutableStateFlow(UiState())
	val state = _state.asStateFlow()

	private val _result = MutableStateFlow(PokemonForm.empty)
	val result = _result.asStateFlow()

	private val _event = MutableSharedFlow<Event>()
	val event = _event.asSharedFlow()

	private var id = ""

	fun init(value: String) {
		if (value.isEmpty()) {
			viewModelScope.launch { _event.emit(Event.OnNavigateBack) }
			return
		}

		id = value
		executeGetPokemonForm()
	}

	fun retry() {
		_state.update { it.copy(error = false) }

		executeGetPokemonForm()
	}

	private fun executeGetPokemonForm() {
		_state.update { it.copy(isLoading = true) }

		getPokemonDetailsUseCase.execute(viewModelScope, request = id, onResult = { result ->
			_state.update { it.copy(isLoading = false) }

			if (result == null || result.isValid.not()) {
				_state.update { it.copy(error = true) }
				return@execute
			}

			viewModelScope.launch { _result.emit(result) }
		})
	}

	data class UiState(val isLoading: Boolean = false, val error: Boolean = false)

	sealed interface Event {
		object OnNavigateBack : Event
	}
}