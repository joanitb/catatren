package com.example.mytodolist.retrofit

import com.example.recyclerviewtest.DatosEstacion
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @GET()
    suspend fun getFromAPI(@Url url: String): Response<List<DatosEstacion>>

    companion object {
        val base_URL = "https://jcibravo.neocities.org/api/"
        fun create(): ApiInterface {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}