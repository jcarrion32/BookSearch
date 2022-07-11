package com.example.googlebookstest.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
[Object] creates a "Singleton" reference
We should have a single reference of the retrofit library
to avoid memory consumption
 */

object RetroFitNetwork {
    private fun initRetroFit() = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: Service by lazy {
        initRetroFit().create(Service::class.java)
    }

}