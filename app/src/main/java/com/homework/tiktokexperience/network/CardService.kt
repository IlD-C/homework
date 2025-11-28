package com.homework.tiktokexperience.network

import com.homework.tiktokexperience.model.cardPageData
import com.homework.tiktokexperience.model.clickData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardService {
    @GET("{page}/{userid}/page.json")
    suspend fun getData(@Path("page") page: Int, @Path("userid") id: String): cardPageData

    @GET("loveButton")
    suspend fun changeLove(@Query("userId") userId: String, @Query("cardId") cardId: String): clickData
}