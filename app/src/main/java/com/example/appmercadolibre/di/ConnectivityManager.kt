package com.example.appmercadolibre.di

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Módulo Dagger Hilt que proporciona dependencias a nivel de aplicación.
 */
@Module
@InstallIn(SingletonComponent::class)
object ConnectivityManager {

    /**
     * Proporciona el contexto de la aplicación.
     *
     * @return [ConnectivityManager] - ConnectivityManager de la aplicación.
     */
    @Provides
    fun provideApplicationContext(@ApplicationContext  context: Context): ConnectivityManager {
        // Se obtiene el servicio CONNECTIVITY_SERVICE del contexto ydevuelve la instancia de ConnectivityManager
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}