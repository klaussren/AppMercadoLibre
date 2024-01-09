package com.example.appmercadolibre

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class AppMercadoLibreAplication: Application() {

    companion object {
        lateinit var applicationContext: Context
            private set
    }


}