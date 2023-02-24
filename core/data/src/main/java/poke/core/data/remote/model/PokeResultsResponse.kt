package poke.core.data.remote.model

import com.google.gson.annotations.SerializedName

class PokeResultsResponse : PaginatedResponse<PokeItemResponse>()

class PokeItemResponse {
	@SerializedName("name")
	val name: String? = null

	@SerializedName("url")
	val url: String? = null
}