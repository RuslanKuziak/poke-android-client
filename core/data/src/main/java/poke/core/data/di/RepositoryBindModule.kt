package poke.core.data.di;

import com.poke.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import poke.core.data.repository.PokemonRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryBindModule {

	@Singleton
	@Binds
	abstract fun providePokemonRepository(repository: PokemonRepositoryImpl): PokemonRepository

}