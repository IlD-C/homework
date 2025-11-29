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
        if (isRefresh) innerState.value = State.Loading//刷新实现
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

    fun touchLove(view: View, id: String) {
        viewModelScope.launch {
            val flag: Boolean = repository.touchLove(id)
            val index = currentList.indexOfFirst { it.id == id }
            currentList[index] = currentList[index].copy(
                loveCount = if (currentList[index].isLove) currentList[index].loveCount - 1 else currentList[index].loveCount + 1,
                isLove = !currentList[index].isLove
            )
            //乐关更新
            if (!flag) {//失败回退并提交一次数据
                Log.d("MainViewModel", "touch:false")
                var newArrayList = ArrayList(currentList)
                newArrayList[index] = currentList[index].copy(
                    loveCount = if (currentList[index].isLove) currentList[index].loveCount - 1 else currentList[index].loveCount + 1,
                    isLove = !currentList[index].isLove
                )//确保与老数据在需要替换的位置不一样
                Log.d("MainViewModel", "false:islove=${newArrayList[index].isLove}")
                innerState.value = State.success(newArrayList) //先提交差异版本再更新老版本
                currentList[index] = newArrayList[index]
            }
            Log.d("MainViewModel", "flag:$flag")
        }
    }
}

sealed class State {
    data class success(val items: List<CardBean>) : State()
    object Loading : State()
    data class Error(val msg: String) : State()
}