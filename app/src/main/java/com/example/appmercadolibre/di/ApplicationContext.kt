package com.example.appmercadolibre.di

import android.content.Context
import com.example.appmercadolibre.AppMercadoLibreAplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Módulo Dagger Hilt que proporciona dependencias a nivel de aplicación.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationContext {

    /**
     * Proporciona el contexto de la aplicación.
     *
     * @return [Context] - Contexto de la aplicación.
     */
    @Provides
    fun provideApplicationContext(): Context {

        return AppMercadoLibreAplication.applicationContext
    }
}