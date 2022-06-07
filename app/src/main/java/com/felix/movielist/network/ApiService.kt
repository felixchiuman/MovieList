package com.felix.movielist.network

import com.felix.movielist.model.comingSoon.GetAllComingSoonResponse
import com.felix.movielist.model.movieDetail.GetAllMovieDetailResponse
import com.felix.movielist.model.playingNow.GetAllPlayingNowResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getAllPlayingNow(): GetAllPlayingNowResponse

    @GET("movie/upcoming")
    suspend fun getAllComingSoon(): GetAllComingSoonResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movieid:Int): GetAllMovieDetailResponse
}