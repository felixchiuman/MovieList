package com.felix.movielist.di

import com.felix.movielist.viewModel.MainPageViewModel
import com.felix.movielist.viewModel.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainPageViewModel)
    viewModelOf(::MovieDetailViewModel)
}