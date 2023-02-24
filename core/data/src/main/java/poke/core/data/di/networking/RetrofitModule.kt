package poke.core.data.di.networking

import com.google.gson.Gson
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import poke.core.data.di.BaseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    @Singleton
    internal fun provide(
        @BaseUrl baseUrl: String,
        client: Lazy<OkHttpClient>,
        gson: Gson
    ): Retrofit {
        return create(baseUrl, client, gson)
    }

    private fun create(baseUrl: String, httpClient: Lazy<OkHttpClient>, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .callFactory { httpClient.get().newCall(it) }
            .build()
    }

}