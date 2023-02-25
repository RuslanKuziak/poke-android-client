package com.poke.domain.usecase

import androidx.paging.PagingData
import com.poke.domain.di.IoDispatcher
import com.poke.domain.model.PokemonItem
import com.poke.domain.repository.PokemonRepository
import com.poke.domain.request.GetListRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsPagedUseCase @Inject constructor(
	private val repository: PokemonRepository,
	@IoDispatcher private val dispatcher: CoroutineDispatcher
) : RequestResultFlowUseCase<GetListRequest, PagingData<PokemonItem>>(dispatcher) {

	override fun invoke(request: GetListRequest): Flow<PagingData<PokemonItem>> {
		return repository.trackPokemonsPaged(request)
	}
}