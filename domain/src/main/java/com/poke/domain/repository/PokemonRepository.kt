package com.poke.domain.repository

import androidx.paging.PagingData
import com.poke.domain.model.PokemonItem
import com.poke.domain.request.GetListRequest
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

	fun trackPokemonsPaged(request: GetListRequest) : Flow<PagingData<PokemonItem>>
}