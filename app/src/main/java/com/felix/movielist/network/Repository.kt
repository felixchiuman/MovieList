package com.felix.movielist.network

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getDetailMovies(movieId : Int) = apiHelper.getMovieDetail(movieId)

    suspend fun getAllPlayingNow() = apiHelper.getAllPlayingNow()

    suspend fun getAllComingSoon() = apiHelper.getAllComingSoon()
}