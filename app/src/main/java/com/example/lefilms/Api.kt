package com.example.lefilms

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "abad1afd4d8059b78feede65b725be51",
        @Query("page") page: Int
    ) : Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "abad1afd4d8059b78feede65b725be51",
        @Query("page") page: Int
    ) : Call<GetMoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "abad1afd4d8059b78feede65b725be51",
        @Query("page") page: Int
    ) : Call<GetMoviesResponse>
}