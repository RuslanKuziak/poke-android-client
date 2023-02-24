package poke.core.data.mapper

import com.poke.domain.model.PokemonItem
import poke.core.data.remote.model.PokeItemResponse

fun PokeItemResponse.toPokeItem() : PokemonItem {
	return PokemonItem(
		name = name.orEmpty(),
		detailsUrl = url.orEmpty()
	)
}