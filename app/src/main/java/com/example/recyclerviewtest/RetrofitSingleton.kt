package com.example.recyclerviewtest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jcibravo.neocities.org/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: ApiService = retrofit.create(ApiService::class.java)
}
