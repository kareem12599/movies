package com.example.movieapp.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

data class Movies(
    val movies: List<Movie>
)

@Entity(tableName = "movies", indices = [Index(value = ["title"], unique = true) ])
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0,
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    val title: String,
    val year: Int
)