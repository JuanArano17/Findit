package com.group.findit.ui.data.game

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import javax.inject.Inject

/**
 * Utility class for checking network connectivity.
 * Provides methods to determine if a network connection is available.
 * @constructor Injects a [ConnectivityManager] instance.
 */
class ConnectivityChecker @Inject constructor(private val connectivityManager: ConnectivityManager) {

    /**
     * Checks if a network connection is available.
     * @return `true` if a connection is available via Wi-Fi or cellular, `false` otherwise.
     */
    fun isConnectionAvailable(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasTransport(TRANSPORT_WIFI) == true ||
                capabilities?.hasTransport(TRANSPORT_CELLULAR) == true
    }
}