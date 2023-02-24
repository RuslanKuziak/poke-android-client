package poke.core.data.remote.model

import com.google.gson.annotations.SerializedName

open class PaginatedResponse<T> {
	@SerializedName("previous")
	val previous: String? = null

	@SerializedName("next")
	val next: String? = null

	@SerializedName("count")
	val count: Int? = null

	@SerializedName("results")
	val values: List<T>? = null
}