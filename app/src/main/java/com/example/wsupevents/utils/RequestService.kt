package com.example.wsupevents.utils

import com.example.wsupevents.network.EndpointApis
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestService {
   // private val BASE_URL = "http://192.168.100.46:81/dEvOps/WsupEvents/api/"
    private val BASE_URL = "http://calista.co.ke/events/api/"
    var gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit(token: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient(token))
            .build()
    }

    private fun getClient(token: String): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", token)
                .build()
            chain.proceed(newRequest)
        }.build()
    }
    fun getService(token: String): EndpointApis {
        return getRetrofit(token).create(EndpointApis::class.java)
    }
}