package com.csapps.whatsthenews.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkConnectivityCallback: ConnectivityManager.NetworkCallback() {

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)

    }

    override fun onLost(network: Network) {
        super.onLost(network)
    }
}