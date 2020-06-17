package com.example.movieapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.remote.PicData
import com.example.movieapp.data.model.Result
import com.example.movieapp.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository: MovieRepository) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UiModel>>? = null
    private val TAG = "MainViewModel"
    private var _moviesPicsLiveData = MutableLiveData<List<PicData>?>()

    val moviesPicsLiveData: LiveData<List<PicData>?>
        get() = _moviesPicsLiveData
    private var _movieLiveData = MutableLiveData<Movie>()

    val movieLiveData: LiveData<Movie>
        get() = _movieLiveData
    init {
        Log.d(TAG, "MainViewModel: viewmodel is working...");
    }

    suspend fun start() =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.parseMovies()

        }


    fun getMovies(queryString: String?): Flow<PagingData<UiModel>> {
        val lastSearchResult = currentSearchResult

        if (queryString == currentQueryValue && lastSearchResult != null) {
            return lastSearchResult
        }

        currentQueryValue = queryString
        val newResult: Flow<PagingData<UiModel>> = repository.searchForMovie(queryString)
            .map { pagingData ->
                pagingData.map {
                    UiModel.MovieItem(it) {
                        loadMovieDetails(it)
                    }
                }
            }
            .map {
                it.insertSeparators<UiModel.MovieItem, UiModel> { before, after ->
                    if (after == null) return@insertSeparators null
                    if (before == null) return@insertSeparators UiModel.SeparatorItem(
                        after?.movie?.year
                    )
                    if (before.movie.year > after.movie.year)
                        return@insertSeparators UiModel.SeparatorItem(
                            after.movie.year
                        )
                    else
                        null
                }
            }
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    private fun loadMovieDetails(movie: Movie) {
        Log.d(TAG, "movie clicked : $movie")

        viewModelScope.launch {
            val result = repository.loadMovieDetails(movie.title)
            _moviesPicsLiveData.value = when (result){
              is Result.Success -> result.data
                else -> null

            }

            _movieLiveData.value = movie

        }
    }


}

sealed class UiModel {
    data class MovieItem(val movie: Movie, var onPageClicked: () -> Unit) : UiModel()
    data class SeparatorItem(val year: Int) : UiModel()
}



