package com.felix.movielist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felix.movielist.model.movieDetail.GetAllMovieDetailResponse
import com.felix.movielist.network.Repository
import com.felix.movielist.network.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailViewModel(private val repository: Repository) : ViewModel() {
    private val _detailMovie = MutableLiveData<Resource<GetAllMovieDetailResponse>>()
    val detailMovie: LiveData<Resource<GetAllMovieDetailResponse>>
        get() = _detailMovie

    fun getAllDetailMovies(movieid: Int){
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getDetailMovies(movieid)))
            }catch (exception: Exception){
                _detailMovie.postValue(Resource.error(exception.message ?: "Error Occured"))
            }
        }
    }
}