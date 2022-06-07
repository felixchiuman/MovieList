package com.felix.movielist.di

import com.felix.movielist.network.Repository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::Repository)
}