package com.pourkazemi.mahdi.kalastore.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}

class NetworkStatusTracker @Inject constructor(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val validNetworkConnections= ArrayList<Network>()

    val networkStatus = callbackFlow<NetworkStatus> {
            val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onUnavailable() {
                    if (validNetworkConnections.isEmpty()) trySend(NetworkStatus.Unavailable)
                        Timber.tag("mahdiTest").d("1overUnavailable")
                }

                override fun onAvailable(network: Network) {
                    val networkCapability = connectivityManager.getNetworkCapabilities(network)
                    val hasNetworkConnection = networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)?:false
                    if (hasNetworkConnection) {
                        validNetworkConnections.add(network)
                        if (validNetworkConnections.isNotEmpty()) trySend(NetworkStatus.Available)
                    }
                }

                override fun onLost(network: Network) {
                    validNetworkConnections.remove(network)
                    if (validNetworkConnections.isEmpty()) trySend(NetworkStatus.Unavailable)
                    Timber.tag("mahdiTest").d("2overUnavailable")
                }
            }
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

            connectivityManager.registerNetworkCallback(request, networkStatusCallback)

            awaitClose {
                close()
                connectivityManager.unregisterNetworkCallback(networkStatusCallback)
            }
    }

}

/*inline fun <Result> Flow<NetworkStatus>.map(
    crossinline onUnavailable: suspend () -> Result,
    crossinline onAvailable: suspend () -> Result,
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}*/
