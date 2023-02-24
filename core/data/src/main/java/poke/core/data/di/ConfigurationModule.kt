package poke.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
object ConfigurationModule {

    private const val BASE_URL = "https://pokeapi.co/api"

    @Provides
    @BaseUrl
    internal fun provideBaseUrl(): String {
        return BASE_URL
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl