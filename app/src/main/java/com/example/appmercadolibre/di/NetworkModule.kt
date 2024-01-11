package com.example.appmercadolibre.di

import com.example.appmercadolibre.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt que proporciona dependencias relacionadas con la red.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Proporciona una instancia de OkHttpClient.
     *
     * @return [OkHttpClient] - Instancia de OkHttpClient.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    /**
     * Proporciona una instancia de Retrofit.
     *
     * @param okHttpClient [OkHttpClient] - Instancia de OkHttpClient.
     * @return [Retrofit] - Instancia de Retrofit.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Proporciona una instancia de ApiService.
     *
     * @param retrofit [Retrofit] - Instancia de Retrofit.
     * @return [ApiService] - Instancia de ApiService.
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}