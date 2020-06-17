package com.example.movieapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "ab7262d2643a733fb1f814c074ca9b1a"
const val API_METHOD = "flickr.photos.search"
const val API_FORMAT = "json"







interface MainNetwork {

    @GET("/services/rest/")
    suspend fun fetchNextTitle(
        @Query("method") method: String = API_METHOD,
        @Query("api_key") key: String = API_KEY,
        @Query("text") title: String,
        @Query("per_page")  per_page:Int = 10,
        @Query("page") page:Int = 1,
        @Query("format") format: String = API_FORMAT
       ): PicModel
}
data class PicModel (
    val photos: Photos,
    val stat: String
)
data class Photos(
    val photo: List<PicData>

)
data class PicData(
    val farm: Int,
    val id: String,
    val secret: String,
    val server: String
)