package com.example.movieapp.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.movieapp.data.db.AppDatabase
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.Result
import com.example.movieapp.data.remote.MoviesRemoteDataSource
import com.example.movieapp.data.remote.PicData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject


class MovieRepository @Inject constructor(
    private val network: MoviesRemoteDataSource,
    private val database: AppDatabase
) {

    var moviesCount: Int = 0;
    internal suspend fun parseMovies() = withContext(Dispatchers.IO) {

        val result = withContext(Dispatchers.IO) {
            try {
                network.getAllMovies()
            } catch (cause: Throwable) {
                throw  MoviesFetchingError("Unable to parse movies", cause)
            }
        }
        yield()
        if (!result.movies.isNullOrEmpty()) {
            moviesCount = result.movies.size
            database.movieDao().insertAll(result.movies)

        } else {
            throw  MoviesFetchingError("Unable to parse movies", null)
        }

    }


    fun searchForMovie(query: String?) =
        getResult { MoviePagingSource(database, query, moviesCount) }


    private fun getResult(source: () -> PagingSource<Int, Movie>): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = source
        ).flow
    }

    suspend fun loadMovieDetails(title: String): Result<List<PicData>> {
        return try {
            Result.Success(network.fetchMovieData(title).photos.photo)
        } catch (ex: Exception) {
            Log.d("exception", "${ex.message}")
            Result.Error(ex)
        }
    }


    companion object {
        private const val PAGE_SIZE = 50
    }
}

class MoviesFetchingError(message: String, cause: Throwable?) : Throwable(message, cause)