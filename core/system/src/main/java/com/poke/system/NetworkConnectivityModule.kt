package com.poke.system

import android.content.Context
import com.poke.system.NetworkManager
import com.poke.system.NetworkManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkConnectivityModule {

	@Provides
	fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager {
		return NetworkManagerImpl(context)
	}
}