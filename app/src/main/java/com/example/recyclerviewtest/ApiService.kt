package com.example.recyclerviewtest

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("estacions.json")
    fun getData(): Call<List<DatosEstacion>>
}
