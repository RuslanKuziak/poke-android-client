package poke.core.data.mapper

import com.poke.domain.model.PokemonItem
import poke.core.data.remote.model.PokeItemResponse
import poke.core.data.remote.paging.PokemonsResult

fun PokemonsResult.toPokeItem() : PokemonItem {
	return PokemonItem(
		name = item.name.orEmpty(),
		total = total
	)
}