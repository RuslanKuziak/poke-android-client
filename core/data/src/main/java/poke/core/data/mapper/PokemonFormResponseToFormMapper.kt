package poke.core.data.mapper

import com.poke.domain.model.PokemonForm
import com.poke.domain.model.Sprites
import com.poke.domain.model.Type
import com.poke.domain.model.TypeSlot
import poke.core.data.remote.model.PokeFormResponse
import poke.core.data.remote.model.SpritesResponse
import poke.core.data.remote.model.TypeResponse
import poke.core.data.remote.model.TypeSlotResponse

fun PokeFormResponse?.toPokeForm(): PokemonForm {
	if (this == null) {
		return PokemonForm.empty
	}

	return PokemonForm(
		name = name.orEmpty(),
		sprites = sprites.toSprites(),
		types = types?.map { it.toTypeSlot() } ?: emptyList()
	)
}

fun SpritesResponse?.toSprites(): Sprites {
	if (this == null) {
		return Sprites.empty
	}

	return Sprites(
		frontDefaultImage = frontDefaultImage.orEmpty()
	)
}

fun TypeSlotResponse.toTypeSlot(): TypeSlot {
	return TypeSlot(
		slot = slot ?: 0,
		type = type.toTypeSlot()
	)
}

fun TypeResponse?.toTypeSlot(): Type {
	if (this == null) {
		return Type()
	}

	return Type(
		name = name.orEmpty()
	)
}
