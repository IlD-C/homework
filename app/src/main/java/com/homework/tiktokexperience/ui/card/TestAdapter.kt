package com.homework.tiktokexperience.ui.card

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

class TestAdapter(private var data: List<CardBean>) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
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
        holder.image.setImageResource(R.drawable.image)
        holder.title.text = data[position].title
        imageTrans(holder.icon, R.drawable.image)
        holder.name.text = data[position].name
        holder.loveCount.text = String.format("%d", data[position].loveCount)

    }

    private fun imageTrans(imageView: ImageView, imageUrl: Int) {//图片转换
        // 使用 Kotlin
        Glide.with(imageView.context)
            .load(imageUrl) // 图片资源，可以是URL、资源ID、文件等
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(imageView) // 你的ImageView
    }
}