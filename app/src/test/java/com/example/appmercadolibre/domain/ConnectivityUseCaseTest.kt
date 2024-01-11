package com.example.appmercadolibre.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ConnectivityUseCaseTest {

    @RelaxedMockK
    private lateinit var context: Context

    @RelaxedMockK
    private lateinit var connectivityManager: ConnectivityManager

    @RelaxedMockK
    private lateinit var network: Network
    @RelaxedMockK
    private lateinit var connectivityUseCase: ConnectivityUseCase


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        connectivityUseCase = ConnectivityUseCase(connectivityManager)
    }

    @Test
    fun testConnectivityUseCaseWithWifi() = runBlocking {
        setupConnectivityCapabilities(isWifiConnected = true, isCellularConnected = false)

        val isConnected = connectivityUseCase.invoke()

        assertTrue(isConnected)
    }

    @Test
    fun testConnectivityUseCaseWithCellular() {
        setupConnectivityCapabilities(isWifiConnected = false, isCellularConnected = true)

        val isConnected = runBlocking { connectivityUseCase.invoke() }

        assertTrue(isConnected)
    }

    @Test
    fun testConnectivityUseCaseWithNoConnection() =runBlocking {
        setupConnectivityCapabilities(isWifiConnected = false, isCellularConnected = false)

        val isConnected =   connectivityUseCase.invoke()

        assertFalse(isConnected)
    }


    private fun setupConnectivityCapabilities(isWifiConnected: Boolean, isCellularConnected: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = NetworkCapabilities().apply {
                if (isWifiConnected) {
                    this.javaClass.getDeclaredMethod("addTransportType", Int::class.java)
                        .invoke(this, NetworkCapabilities.TRANSPORT_WIFI)
                }
                if (isCellularConnected) {
                    this.javaClass.getDeclaredMethod("addTransportType", Int::class.java)
                        .invoke(this, NetworkCapabilities.TRANSPORT_CELLULAR)
                }
            }

            every{connectivityManager.activeNetwork} returns network
            every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities

        }
    }



}