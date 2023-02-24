package poke.core.data.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SerializationModule {

	@Provides
	fun provideGson(): Gson {
		return Gson()
	}

}