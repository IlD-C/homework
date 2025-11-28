package com.homework.tiktokexperience.viewmodel

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.tiktokexperience.R
import com.homework.tiktokexperience.repository.MyRepository
import com.homework.tiktokexperience.ui.card.CardBean
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MyRepository) : ViewModel() {
    private val innerState = MutableLiveData<State>()
    val state: LiveData<State> = innerState
    private val currentList = mutableListOf<CardBean>()
    private var page = 1
    fun loadData(isRefresh: Boolean = false) {//刷新或者直接加载指定的页面
        if (isRefresh) innerState.value = State.Loading
        viewModelScope.launch {
            if (isRefresh) {//刷新
                page = 1
                currentList.clear()
            }
            val newItems = repository.getPage(page)
            if (newItems.isSuccess) {
                currentList.addAll(newItems.getOrDefault(emptyList()))
                innerState.value = State.success(currentList.toList())//将currentList转换为不可变列表且为新表
                page++
            } else {
                innerState.value = State.Error("Unknown error")
            }
        }
    }

    fun touchLove(view: View, bean: CardBean) {
        viewModelScope.launch {
            val flag: Boolean = repository.touchLove(bean.id)
            if (!flag) {
                val loveIcon = view.findViewById<ImageView>(R.id.loveIcon)
                val loveCount = view.findViewById<TextView>(R.id.loveCount)
                if (loveIcon.isSelected) bean.loveCount-- else bean.loveCount++
                loveCount.text = bean.loveCount.toString()
                loveIcon.isSelected = !loveIcon.isSelected
                bean.isLove = loveIcon.isSelected
            }
        }
    }
}

sealed class State {
    data class success(val items: List<CardBean>) : State()
    object Loading : State()
    data class Error(val msg: String) : State()
}