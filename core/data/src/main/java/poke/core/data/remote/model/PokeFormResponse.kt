package poke.core.data.remote.model

import com.google.gson.annotations.SerializedName

class PokeFormResponse {
	@SerializedName("sprites")
	val sprites: SpritesResponse? = null

	@SerializedName("types")
	val types: List<TypeSlotResponse>? = null
}

class SpritesResponse {
	@SerializedName("front_default")
	val frontDefaultImage: String? = null
}

class TypeSlotResponse {
	@SerializedName("slot")
	val slot: Int? = null

	@SerializedName("type")
	val type: TypeResponse? = null
}

class TypeResponse {
	@SerializedName("name")
	val name: String? = null
}