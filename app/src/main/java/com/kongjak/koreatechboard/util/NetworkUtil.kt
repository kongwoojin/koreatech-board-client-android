package com.kongjak.koreatechboard.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.kongjak.koreatechboard.ui.state.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkUtil @Inject constructor(context: Context) {
    private val connectivityManager = context.getSystemService(ConnectivityManager::class.java)

    fun networkState() = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkState.Connected)
            }

            override fun onLost(network: Network) {
                trySend(NetworkState.Disconnected)
            }

            override fun onUnavailable() {
                trySend(NetworkState.Disconnected)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}