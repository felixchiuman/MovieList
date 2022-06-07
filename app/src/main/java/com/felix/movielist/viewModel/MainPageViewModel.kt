package com.felix.movielist.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felix.movielist.model.comingSoon.GetAllComingSoonResponse
import com.felix.movielist.model.playingNow.GetAllPlayingNowResponse
import com.felix.movielist.network.Repository
import com.felix.movielist.network.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class MainPageViewModel(private val repository: Repository): ViewModel() {
    private val _playingNow = MutableLiveData<Resource<GetAllPlayingNowResponse>>()
    private val _comingSoon = MutableLiveData<Resource<GetAllComingSoonResponse>>()
    val playingNow: LiveData<Resource<GetAllPlayingNowResponse>> get() = _playingNow
    val comingSoon: LiveData<Resource<GetAllComingSoonResponse>> get() = _comingSoon

    fun getAllPlayingNow(){
        viewModelScope.launch {
            _playingNow.postValue(Resource.loading())
            try {
                _playingNow.postValue(Resource.success(repository.getAllPlayingNow()))
            }catch (exception : Exception){
                _playingNow.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }

    fun getAllComingSoon(){
        viewModelScope.launch {
            _comingSoon.postValue(Resource.loading())
            try {
                _comingSoon.postValue(Resource.success(repository.getAllComingSoon()))
            }catch (exception : Exception){
                _comingSoon.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }
}