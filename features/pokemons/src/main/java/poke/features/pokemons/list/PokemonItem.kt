package poke.features.pokemons.list

import com.poke.domain.model.PokemonItem

data class PokemonItemModel(val name: String, val total : Int)

fun PokemonItem.toModel(): PokemonItemModel {
	return PokemonItemModel(
		name = name,
		total = total
	)
}