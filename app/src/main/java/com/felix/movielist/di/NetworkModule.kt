package com.felix.movielist.di

import com.felix.movielist.network.ApiHelper
import com.felix.movielist.network.ApiService
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val BASE_URL = "http://api.themoviedb.org/3/"
val networkModule = module {
    single{
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val url = original.url.newBuilder()
                    .addQueryParameter("api_key", "00dfa9ebae2c776e7509c85faa9a2e23")
                    .build()

                val request = original.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    //membuat instance retrofit (ciri2 instance ada builder atau build di akhir)
    single{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    //membuat instance api service
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
    singleOf(::ApiHelper)
//    single{ApiHelper(get())} -> sama aj kaya di atas
}