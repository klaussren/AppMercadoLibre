package com.example.appmercadolibre.di

import android.content.Context
import com.example.appmercadolibre.AppMercadoLibreAplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationContext {

    @Provides
    fun provideApplicationContext(): Context {

        return AppMercadoLibreAplication.applicationContext
    }
}