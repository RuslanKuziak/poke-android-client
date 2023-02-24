package poke.core.data.remote.service

import poke.core.data.remote.model.PokeResultsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApiService {

	@GET("/v2/pokemon")
	suspend fun getPokemonList(
		@Query("limit") limit: Int,
		@Query("offset") offset: Int
	): Response<PokeResultsResponse>

	@GET
	suspend fun getPokemonsByUrl(@Url url: String): Response<PokeResultsResponse>
}