package com.poke.domain.usecase

import com.poke.domain.di.IoDispatcher
import com.poke.domain.di.MainDispatcher
import com.poke.domain.model.PokemonForm
import com.poke.domain.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(
	private val repository: PokemonRepository,
	@IoDispatcher private val execution: CoroutineDispatcher,
	@MainDispatcher private val subscription: CoroutineDispatcher,
) : RequestResultUseCase<String, PokemonForm>(execution, subscription) {

	override suspend fun invoke(request: String): PokemonForm {
		return repository.getFormById(id = request)
	}
}