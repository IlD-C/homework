package com.homework.tiktokexperience.ui.card

import java.net.URL

data class CardBean(
    val id: String,//唯一id
    val imageURL: String,
    val imageHigh: Int,
    val imageWidth: Int,
    val title: String,
    val iconURL: String,
    val name: String,
    val loveCount: Int,
    val isLove: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || javaClass != other.javaClass) return false
        val cardBean = other as CardBean
        return imageURL == cardBean.imageURL
                && title == cardBean.title
                && iconURL == cardBean.iconURL
                && name == cardBean.name
                && loveCount == cardBean.loveCount
                && isLove == cardBean.isLove
    }

    override fun hashCode(): Int {
        return imageURL.hashCode() + title.hashCode() + iconURL.hashCode() + name.hashCode() + loveCount.hashCode() + isLove.hashCode();
    }
}
