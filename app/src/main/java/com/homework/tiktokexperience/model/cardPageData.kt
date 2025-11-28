package com.homework.tiktokexperience.model

import com.homework.tiktokexperience.ui.card.CardBean


data class cardPageData(
    val code: Int,
    val `data`: List<CardBean>,
    val request_id: String,
    val success: Boolean,
    val time: Int,
)