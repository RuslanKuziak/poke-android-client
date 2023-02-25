package poke.core.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.poke.domain.request.GetListRequest
import poke.core.data.remote.model.PokeItemResponse
import poke.core.data.remote.service.PokeApiService

private const val TAG = "PokemonsPagingSource"

class PokemonsPagingSource(
	private val service: PokeApiService,
	private val request: GetListRequest
) : PagingSource<String, PokemonsResult>() {

	override fun getRefreshKey(state: PagingState<String, PokemonsResult>): String? {
		return null
	}

	override suspend fun load(params: LoadParams<String>): LoadResult<String, PokemonsResult> {
		val page = params.key ?: ""

		return try {
			val response = when {
				page.isEmpty() -> service.getPokemonList(limit = request.pageSize, offset = 0)
				else -> service.getPokemonsByUrl(page)
			}

			val result = response.body()
			val values = result?.values.orEmpty()

			if (values.isEmpty()) {
				LoadResult.Page(
					data = emptyList(),
					prevKey = result?.previous,
					nextKey = result?.next
				)
			}

			LoadResult.Page(
				data = values.map {
					PokemonsResult(
						total = result?.count ?: 0,
						item = it
					)
				},
				prevKey = result?.previous,
				nextKey = result?.next
			)
		} catch (ex: Exception) {
			Log.e(TAG, "Failed to retrieve Pokemons Paged")
			LoadResult.Error(ex)
		}
	}

}