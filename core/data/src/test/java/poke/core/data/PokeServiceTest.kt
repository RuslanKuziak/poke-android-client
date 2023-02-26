package poke.core.data

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import poke.core.data.remote.model.PokeFormResponse
import poke.core.data.remote.service.PokeApiService
import poke.core.data.utils.getResourceAsString
import poke.core.data.utils.newTestRetrofitClient
import java.net.HttpURLConnection

class PokeServiceTest {

	private enum class Resources(val path: String) {
		POKEMONS_SUCCESS("pokemon_list_success.json"),
		POKEMON_FORM_SUCCESS("pokemon_form_success.json"),
		POKEMONS_EMPTY("pokemon_list_empty.json"),
	}

	private val webServer = MockWebServer()

	private lateinit var service: PokeApiService

	@Before
	fun setup() {
		webServer.start()

		service =
			newTestRetrofitClient(baseUrl = webServer.url("/")).create(PokeApiService::class.java)
	}

	@After
	fun tearDown() {
		webServer.shutdown()
	}

	@Test
	fun `retrieve pokemon list success`() = runBlocking {
		val body = getResourceAsString(Resources.POKEMONS_SUCCESS.path)

		val mockResponse = MockResponse()
			.setResponseCode(HttpURLConnection.HTTP_OK)
			.setBody(body)

		webServer.enqueue(mockResponse)

		val response = service.getPokemonList(limit = 5, offset = 0).body()

		Assert.assertFalse(response == null)
		Assert.assertFalse(response?.values.isNullOrEmpty())
	}

	@Test
	fun `retrieve pokemon list with too large offset`() = runBlocking {
		val body = getResourceAsString(Resources.POKEMONS_EMPTY.path)

		val mockResponse = MockResponse()
			.setResponseCode(HttpURLConnection.HTTP_OK)
			.setBody(body)

		webServer.enqueue(mockResponse)

		val response = service.getPokemonList(limit = 5, offset = 5000).body()

		Assert.assertFalse(response == null)
		Assert.assertFalse(response?.values == null)
		Assert.assertTrue(response?.values?.isEmpty() == true)
	}

	@Test
	fun `retrieve pokemon list with wrong offset and limit`() = runBlocking {
		val body = getResourceAsString(Resources.POKEMONS_EMPTY.path)

		val mockResponse = MockResponse()
			.setResponseCode(HttpURLConnection.HTTP_OK)
			.setBody(body)

		webServer.enqueue(mockResponse)

		val response =
			service.getPokemonsByUrl(url = "https://pokeapi.co/api/v2/pokemon?offset=-1&limit=-1")

		Assert.assertTrue(response.isSuccessful)
		Assert.assertTrue(response.body()?.values.isNullOrEmpty())
	}

	@Test
	fun `retrieve pokemon form success`() = runBlocking {
		val body = getResourceAsString(Resources.POKEMON_FORM_SUCCESS.path)

		val mockResponse = MockResponse()
			.setResponseCode(HttpURLConnection.HTTP_OK)
			.setBody(body)

		webServer.enqueue(mockResponse)

		val response = service.getPokemonForm(id = "1")

		Assert.assertTrue(response.body().isValid())
	}

	@Test
	fun `pokemon form contains image success`() = runBlocking {
		val body = getResourceAsString(Resources.POKEMON_FORM_SUCCESS.path)

		val mockResponse = MockResponse()
			.setResponseCode(HttpURLConnection.HTTP_OK)
			.setBody(body)

		webServer.enqueue(mockResponse)

		val response = service.getPokemonForm(id = "1")

		Assert.assertTrue(response.body().isValid())
		Assert.assertTrue(response.body().hasImage())
	}

	private fun PokeFormResponse?.isValid(): Boolean {
		if (this == null) {
			return false
		}

		return name != null && sprites != null && types != null
	}

	private fun PokeFormResponse?.hasImage(): Boolean {
		if (this == null) {
			return false
		}

		return sprites?.frontDefaultImage.isNullOrEmpty().not()
	}

}