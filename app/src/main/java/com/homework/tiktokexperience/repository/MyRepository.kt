package com.homework.tiktokexperience.repository

import android.content.res.Resources
import android.util.Log
import com.homework.tiktokexperience.app.App
import com.homework.tiktokexperience.model.cardPageData
import com.homework.tiktokexperience.network.CardNetWork
import com.homework.tiktokexperience.ui.card.CardBean

object MyRepository {//未设置数据库，，只封装网络请求

    suspend fun getPage(page: Int): Result<List<CardBean>> {
        try {
            var data = CardNetWork.cardService.getData(page, "userId")
            return Result.success(data.data)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun touchLove(id: String): Boolean {
        try {
            val data = CardNetWork.cardService.changeLove(id, "userId")
            Log.d("MyRepository", "touch:$data")
            return data.success
        } catch (e: Exception) {
            return false
        }
    }
}