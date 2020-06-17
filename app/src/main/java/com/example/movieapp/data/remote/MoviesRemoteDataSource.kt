package com.example.movieapp.data.remote

import android.content.res.AssetManager
import com.example.movieapp.data.model.Movies
import com.example.movieapp.util.readAssetsFile
import com.google.gson.Gson
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val assetManager: AssetManager,
    private val network: MainNetwork
) {

    fun getAllMovies(): Movies =
        Gson().fromJson(assetManager.readAssetsFile("movies.json"), Movies::class.java)


    suspend fun fetchMovieData(query: String) = network.fetchNextTitle(title = query)

}