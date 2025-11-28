package com.homework.tiktokexperience.network

import android.view.WindowInsetsAnimation
import com.homework.tiktokexperience.app.App
import com.homework.tiktokexperience.model.cardPageData
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object CardNetWork {

    val cardService = ServiceCreator.create<CardService>()

//    suspend fun getPage(page: Int): Result<cardPageData> {
//        return try {
//            val data = cardService.getData(page, App.userId)
//            Result.success(data)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    suspend fun changeLove(cardId: String): Result<Boolean> {
//        return try {
//            val data = cardService.changeLove(cardId, App.userId)
//            Result.success(data)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }


}

