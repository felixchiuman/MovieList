package com.felix.movielist.network

class ApiHelper(val apiService: ApiService) {
    suspend fun getAllPlayingNow() = apiService.getAllPlayingNow()

    suspend fun getAllComingSoon() = apiService.getAllComingSoon()

    suspend fun getMovieDetail(movieId : Int) = apiService.getMovieDetail(movieId)
}