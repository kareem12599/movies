package com.example.movieapp.data

import androidx.paging.PagingSource
import com.example.movieapp.data.db.AppDatabase
import com.example.movieapp.data.model.Movie
import java.io.IOException


class MoviePagingSource(
    private val database: AppDatabase, private val query: String?,
    private val moviesCount: Int
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        var itemsCount = 0

        return try {
            val movies = if (query.isNullOrEmpty()) {
                database.movieDao().getAllMovies()
            } else {
                val dbQuery = "%${query.replace(' ', '%')}%"
              val data  =  database.movieDao().getMovieByName(dbQuery)

                  data.groupByTo(mutableMapOf()) { it.year }.map {map ->
                      map.value.filterIndexed{ index, _ ->  index < 5}

                }.flatten()



            }

            itemsCount = movies.size

            if (movies.isNullOrEmpty())
                LoadResult.Error<Int, Movie>(MoviesFetchingError("No data loaded", null))

            LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = if (params is LoadParams.Refresh) null else if (itemsCount < moviesCount) itemsCount + 1 else null
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }


}