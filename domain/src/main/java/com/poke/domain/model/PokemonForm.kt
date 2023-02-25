package com.poke.domain.model

data class PokemonForm(
	val sprites: Sprites = Sprites.empty,
	val types: List<TypeSlot> = emptyList()
) {

	companion object {
		val empty = PokemonForm()
	}
}

data class Sprites(
	val frontDefaultImage: String = ""
) {
	companion object {
		val empty = Sprites()
	}
}

data class TypeSlot(
	val slot: Int,
	val type: Type
)

data class Type(
	val name: String
)