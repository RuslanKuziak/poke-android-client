package poke.core.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.poke.domain.model.PokemonForm
import com.poke.domain.model.PokemonItem
import com.poke.domain.repository.PokemonRepository
import com.poke.domain.request.GetListRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import poke.core.data.mapper.toPokeForm
import poke.core.data.mapper.toPokeItem
import poke.core.data.remote.paging.PokemonsPagingSource
import poke.core.data.remote.service.PokeApiService
import javax.inject.Inject

private const val TAG = "PokemonRepository"
private const val PAGING_MAX_SIZE = 75

class PokemonRepositoryImpl @Inject constructor(
	private val service: PokeApiService
) : PokemonRepository {

	override suspend fun getFormById(id: String): PokemonForm {
		val response = service.getPokemonForm(id)

		if (response.isSuccessful.not()) {
			Log.e(TAG, "Failed to load pokemon form. ID: $id")
			return PokemonForm.empty
		}

		return response.body().toPokeForm()
	}

	override fun trackPokemonsPaged(request: GetListRequest): Flow<PagingData<PokemonItem>> {
		return Pager(config = PagingConfig(
			initialLoadSize = request.pageSize,
			pageSize = request.pageSize,
			maxSize = PAGING_MAX_SIZE,
			enablePlaceholders = false
		), pagingSourceFactory = {
			PokemonsPagingSource(service, request)
		}).flow.map { it.map { result -> result.toPokeItem() } }
	}

}