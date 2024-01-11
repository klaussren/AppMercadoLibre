package com.example.appmercadolibre.domain

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

/**
 * Caso de uso que verifica la conectividad de red del dispositivo.
 *
 * @property connectivityManager [ConnectivityManager] - Gestor de conectividad.
 * @constructor Crea una instancia de [ConnectivityUseCase].
 */
class ConnectivityUseCase @Inject constructor(private val connectivityManager: ConnectivityManager) {

    /**
     * Verifica si hay una conexión de red activa en el dispositivo.
     *
     * @return `true` si hay conexión, `false` de lo contrario.
     */
    @SuppressLint("ServiceCast")
     operator fun invoke(): Boolean {
               return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager?.activeNetwork
            val capabilities = connectivityManager?.getNetworkCapabilities(network)
            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ))
        } else {
            val networkInfo = connectivityManager?.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }
}