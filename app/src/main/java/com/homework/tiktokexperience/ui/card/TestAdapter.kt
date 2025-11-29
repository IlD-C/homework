package com.homework.tiktokexperience.ui.card

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.homework.tiktokexperience.R


// 测试类
class TestAdapter(private val context: Context, var data: List<CardBean>) :
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private val itemWidth: Int//item宽度px

    init {
        val screenWidth = context.resources.displayMetrics.widthPixels//屏幕宽度px
        itemWidth = (screenWidth - dp2px(context, 16f)) / 2;//减去4个margin
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density// 屏幕密度
        return (dpValue * scale + 0.5f).toInt()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cardImage)
        val title: TextView = itemView.findViewById(R.id.cardTitle)
        val icon: ImageView = itemView.findViewById(R.id.userIcon)
        val name: TextView = itemView.findViewById(R.id.userName)
        val loveCount: TextView = itemView.findViewById(R.id.loveCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //动态调整item宽高比
        val url: Int = R.drawable.image//test

        var ratio: Double = getRatio(url)//
        if (ratio < 0.7) {
            ratio = 0.7
        } else if (ratio > 1.5) {
            ratio = 1.5
        }

        val targetHeight: Int = (itemWidth * ratio).toInt()//直接使用item宽度计算
        holder.image.setImageResource(R.drawable.image)
        holder.image.layoutParams.height = targetHeight

        Glide.with(context)
            .load(url)
            .override(itemWidth, targetHeight)
            .centerCrop()
            .into(holder.image)

        holder.title.text = data[position].title
        imageTrans(holder.icon, url)//
        holder.name.text = data[position].name
        holder.loveCount.text = String.format("%d", data[position].loveCount)

    }

    private fun getRatio(url: Int): Double {//高宽比
        return 1.0;
    }

    private fun imageTrans(imageView: ImageView, imageUrl: Int) {//图片转换
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView)
    }
}