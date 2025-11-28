package com.homework.tiktokexperience.repository

import android.content.res.Resources
import com.homework.tiktokexperience.app.App
import com.homework.tiktokexperience.model.cardPageData
import com.homework.tiktokexperience.network.CardNetWork
import com.homework.tiktokexperience.ui.card.CardBean

object MyRepository {

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
            var data = CardNetWork.cardService.changeLove(id, "userId")
            return data.success
        } catch (e: Exception) {
            return false
        }
    }
}