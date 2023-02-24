package poke.core.data.di.networking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import poke.core.data.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private const val TIMEOUT = 30L

@InstallIn(SingletonComponent::class)
@Module
object OkHttpClientModule {

	@Provides
	@Singleton
	internal fun provide(): OkHttpClient {
		val builder = createHttpClientBuilder()

		if (BuildConfig.DEBUG) {
			builder.addInterceptor(HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			})
		}

		return builder.build()
	}

	private fun createHttpClientBuilder(): OkHttpClient.Builder {
		return OkHttpClient.Builder()
			.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
			.readTimeout(TIMEOUT, TimeUnit.SECONDS)
			.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
	}

}