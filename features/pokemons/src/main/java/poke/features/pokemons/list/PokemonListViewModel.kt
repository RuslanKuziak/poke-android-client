package poke.features.pokemons.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.poke.domain.request.GetListRequest
import com.poke.domain.usecase.GetPokemonsPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 25

@HiltViewModel
class PokemonListViewModel @Inject constructor(
	private val getPokemonsPagedUseCase: GetPokemonsPagedUseCase
) : ViewModel() {

	private val _data = MutableStateFlow<PagingData<PokemonItemModel>>(PagingData.empty())
	val data = _data.asStateFlow()

	init {
		executeGetPokemons()
	}

	fun onItemSelected(id: String) {
		if (id.isEmpty()) {
			return
		}

		viewModelScope.launch {
		}
	}

	private fun executeGetPokemons() = viewModelScope.launch {
		getPokemonsPagedUseCase.execute(request = GetListRequest(pageSize = PAGE_SIZE))
			.cachedIn(this)
			.map { it.map { item -> item.toModel() } }
			.collectLatest {
				_data.value = it
			}
	}

}