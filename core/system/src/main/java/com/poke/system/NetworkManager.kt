package com.poke.system

import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface NetworkManager {
	fun hasActiveConnection(): Boolean
}

class NetworkManagerImpl(appContext: Context) : NetworkManager {

	private val manager = appContext.getSystemService(ConnectivityManager::class.java)

	override fun hasActiveConnection(): Boolean {
		return manager.activeNetwork != null
	}
}