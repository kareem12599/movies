package com.example.movieapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListToString(listOfString:List<String> ):String{
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun fromString(string:String):List<String>{
        return Gson().fromJson(string, object : TypeToken<List<String>>(){}.type)

    }
}