package poke.core.data.utils

import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun newTestRetrofitClient(baseUrl: HttpUrl, gson: Gson = Gson()): Retrofit {
	val client = OkHttpClient.Builder()
		.connectTimeout(2L, TimeUnit.SECONDS)
		.readTimeout(2L, TimeUnit.SECONDS)
		.build()

	return Retrofit.Builder()
		.baseUrl(baseUrl)
		.addConverterFactory(GsonConverterFactory.create(gson))
		.client(client)
		.build()
}