package com.example.movieapp.data.db
import androidx.room.*
import com.example.movieapp.data.model.Movie


const val DATABASE_NAME = "movie.db"

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
