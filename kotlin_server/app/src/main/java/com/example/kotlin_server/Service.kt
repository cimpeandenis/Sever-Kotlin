package com.example.kotlin_server

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object Service {
    private const val URL = "http://10.0.2.2:8000/"
    lateinit var repository: PhoneRepository

    interface Service {

        @GET("/accessories/")
        suspend fun getItems(): List<Phone>

        @POST("/accessories/new/")
        suspend fun insert(@Body t: Phone): Phone

        @DELETE("/accessories/delete/{id}")
        suspend fun delete(@Path("id") id: String): Response<Unit>

        @PUT("/accessories/update/{id}")
        suspend fun update(@Path("id") id: String, @Body t: Phone): Long
    }

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()


    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)
}