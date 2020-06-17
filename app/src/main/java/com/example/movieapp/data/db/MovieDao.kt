package com.example.movieapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Query("select * from movies order by year desc")
    suspend fun getAllMovies(): List<Movie>


    @Query("select * from movies where title like :queryString order by year desc")
    suspend fun getMovieByName(queryString: String): List<Movie>


    @Query("delete from movies")
    suspend fun clearMovies()


}