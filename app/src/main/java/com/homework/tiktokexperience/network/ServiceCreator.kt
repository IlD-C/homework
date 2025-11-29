package com.homework.tiktokexperience.network

import com.homework.tiktokexperience.app.App
import com.homework.tiktokexperience.mock.MockInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://127.0.0.1/"
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(MockInterceptor(App.context)).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)//加入拦截器的client
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
    inline fun <reified T> create(): T = create(T::class.java)
}