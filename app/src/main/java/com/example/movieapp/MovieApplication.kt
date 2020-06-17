package com.example.movieapp

import android.app.Application
import android.content.Context
import com.example.movieapp.di.core.DaggerAppComponent

class MovieApplication:Application() {
    private val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) =
            (context.applicationContext as MovieApplication).appComponent
    }
}