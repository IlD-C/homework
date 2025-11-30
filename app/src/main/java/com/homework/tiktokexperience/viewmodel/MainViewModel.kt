package com.homework.tiktokexperience.viewmodel

import android.media.Image
import android.util.Log
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

class MainViewModel() : ViewModel() {
    private val repository = MyRepository
    private val innerState = MutableLiveData<State>()
    val state: LiveData<State> = innerState//暴露不可变版本的引用
    private val currentList = mutableListOf<CardBean>()
    private var page = 1
    fun loadData(isRefresh: Boolean = false) {//刷新或者直接加载指定的页面
        innerState.value = State.Loading//刷新实现
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
                innerState.value = State.Error("Net error")
            }
        }
    }

    fun touchLove(view: View, index: Int) {
        viewModelScope.launch {
            if (index == -1) return@launch
            val old = currentList[index]
            val flag = repository.touchLove(old.id)  // 先请求
            if (flag) { // 只在成功时更新数据
                val new = old.copy(
                    loveCount = if (old.isLove) old.loveCount - 1 else old.loveCount + 1,
                    isLove = !old.isLove
                )
                currentList[index] = new
                innerState.value = State.success(currentList.toList())
            }
        }
    }
}

sealed class State {
    data class success(val items: List<CardBean>) : State()
    object Loading : State()
    data class Error(val msg: String) : State()
}