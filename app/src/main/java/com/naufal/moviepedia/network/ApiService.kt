package com.naufal.moviepedia.network

import com.naufal.moviepedia.model.DetailMovieResponse
import com.naufal.moviepedia.model.DetailTVResponse
import com.naufal.moviepedia.model.MovieResponse
import com.naufal.moviepedia.model.ShowResponse
import com.naufal.moviepedia.util.Constant.Companion.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/now_playing")
    fun getMovies(
        @Query("api_key") api : String = API_KEY,
        @Query("language") language : String = "en-US"
    ) : Call<MovieResponse>

    @GET("3/tv/on_the_air")
    fun getShows(
        @Query("api_key") api : String = API_KEY,
        @Query("language") language : String = "en-US"
    ) : Call<ShowResponse>

    @GET("3/movie/{id}")
    fun getDetailMovie(
        @Path("id") id : Int,
        @Query("api_key") api : String = API_KEY,
        @Query("language") language : String = "en-US"
    ) : Call<DetailMovieResponse>

    @GET("3/tv/{id}")
    fun getDetailShow(
        @Path("id") id : Int,
        @Query("api_key") api : String = API_KEY,
        @Query("language") language : String = "en-US"
    ) : Call<DetailTVResponse>

}