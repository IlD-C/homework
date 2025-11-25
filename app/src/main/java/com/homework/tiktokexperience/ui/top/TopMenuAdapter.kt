package com.homework.tiktokexperience.ui.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.homework.tiktokexperience.R

class TopMenuAdapter(private var menu: List<String>) :
    RecyclerView.Adapter<TopMenuAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById<TextView>(R.id.top_MenuName)
    }

    private var selectPosition: Int = 1;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMenuAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.top_menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopMenuAdapter.ViewHolder, position: Int) {
        holder.text.text = menu[position]//binding
        holder.text.isSelected = selectPosition == position
        holder.itemView.setOnClickListener {
            val previousPosition = selectPosition
            selectPosition = holder.bindingAdapterPosition
            // 4. 局部刷新：通知旧的变回普通，通知新的变成选中
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectPosition)
        }
    }

    override fun getItemCount(): Int = menu.size

}