package com.poke.domain.model

data class PokemonForm(
	val name: String = "",
	val sprites: Sprites = Sprites.empty,
	val types: List<TypeSlot> = emptyList()
) {

	val isValid: Boolean
		get() {
			return name.isNotEmpty() && sprites.isValid && types.isNotEmpty()
		}

	companion object {
		val empty = PokemonForm()
	}
}

data class Sprites(
	val frontDefaultImage: String = ""
) {
	val isValid: Boolean
		get() {
			return frontDefaultImage.isNotEmpty()
		}

	companion object {
		val empty = Sprites()
	}
}

data class TypeSlot(
	val slot: Int,
	val type: Type
)

data class Type(
	val name: String = ""
)