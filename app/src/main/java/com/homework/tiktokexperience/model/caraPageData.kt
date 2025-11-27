package com.homework.tiktokexperience.model


data class caraPageData(
    val code: Int,
    val `data`: List<Data>,
    val request_id: String,
    val success: Boolean,
    val time: Int,
) {
    data class Data(
        val image: String,
        val imageHigh: Int,
        val imageWidth: Int,
        val author: String,
        val author_icon: String,
        val comment_id: Int,
        val title: String
    )
}