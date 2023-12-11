package com.yourpackage.api

import com.yourpackage.api.com.example.kotlin_faceguard.KnownFace
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface ApiService {
    @GET("known-faces")
    suspend fun getKnownFaces(): List<KnownFace>

}



object RetrofitService {
    private const val BASE_URL = "https://demo8613047.mockable.io/" // Replace with your base URL

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
