package com.example.movieapp.di.core

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import androidx.room.Room
import com.example.movieapp.data.db.AppDatabase
import com.example.movieapp.data.db.DATABASE_NAME
import com.example.movieapp.data.remote.MainNetwork
import com.example.movieapp.data.remote.MoviesRemoteDataSource
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object AppModule {


    @Singleton
    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(logger())
        .build()

    @Provides
    fun logger(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message);
            }
        })
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    @Singleton
    @Provides
    fun provideMainNetwork(): MainNetwork = Retrofit.Builder()
        .baseUrl("https://www.flickr.com")
        .client(okHttpClient())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build()
        .create(MainNetwork::class.java)


    @Singleton
    @Provides
    fun provideDataBase(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    )
        .build()

    @Singleton
    @Provides
    fun provideAssetManager(context: Context) = context.assets

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(assetManager: AssetManager, mainNetwork: MainNetwork) =
        MoviesRemoteDataSource(
            assetManager,
            mainNetwork
        )

}
