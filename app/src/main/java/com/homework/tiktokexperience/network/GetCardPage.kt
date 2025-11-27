package com.homework.tiktokexperience.network

import com.homework.tiktokexperience.model.caraPageData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GetCardPage {
    @GET("{page}/get_data.json")
    fun getData(@Path("page") page: Int): Call<caraPageData>
}