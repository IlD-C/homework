package com.homework.tiktokexperience.ui.card

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.homework.tiktokexperience.R

class CardAdapter(context: Context, private val onItemClick: (View) -> Unit) :
    ListAdapter<CardBean, CardAdapter.CardViewHolder>(CardDiffCallback()) {
    private val itemWidth: Int//item宽度px

    init {
        val screenWidth = context.resources.displayMetrics.widthPixels//屏幕宽度px
        itemWidth = (screenWidth - dp2px(context, 16f)) / 2;//减去4个margin
    }

    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density// 屏幕密度
        return (dpValue * scale + 0.5f).toInt()
    }

    inner class CardViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            val image: ImageView = itemView.findViewById(R.id.cardImage)
            val title: TextView = itemView.findViewById(R.id.cardTitle)
            val icon: ImageView = itemView.findViewById(R.id.userIcon)
            val name: TextView = itemView.findViewById(R.id.userName)
            val loveCount: TextView = itemView.findViewById(R.id.loveCount)
            val bean: CardBean = getItem(position)
            var ratio: Double = 1.0 * bean.imageHigh / bean.imageWidth
            if (ratio < 0.7) {//自适应的图片高
                ratio = 0.7
            } else if (ratio > 1.3) {
                ratio = 1.3
            }
            val targetHeight: Int = (itemWidth * ratio).toInt()
            title.text = bean.title
            name.text = bean.name
            loveCount.text = bean.loveCount.toString()
            itemView.findViewById<ImageView>(R.id.loveIcon).setOnClickListener(onItemClick)//在显示文字后可点击
            Glide.with(itemView.context)
                .load(bean.iconURL)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .placeholder(R.drawable.default_background)
                .error(R.drawable.default_background)
                .fallback(R.drawable.default_background)
                .into(icon) // 头像加载
            Glide.with(itemView.context)
                .load(bean.imageURL)
                .override(itemWidth, targetHeight)
                .centerCrop()
                .placeholder(R.drawable.default_background)
                .error(R.drawable.default_background)
                .fallback(R.drawable.default_background)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        var inflate = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position)
    }
}

class CardDiffCallback : DiffUtil.ItemCallback<CardBean>() {
    override fun areItemsTheSame(oldItem: CardBean, newItem: CardBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardBean, newItem: CardBean): Boolean {
        return oldItem == newItem
    }
}
