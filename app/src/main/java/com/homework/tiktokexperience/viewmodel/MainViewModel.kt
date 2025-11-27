package com.homework.tiktokexperience.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.tiktokexperience.repository.MyRepository
import com.homework.tiktokexperience.ui.card.CardBean
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val repositry: MyRepository) : ViewModel() {
    private val innerState = MutableLiveData<State>()
    val state: LiveData<State> = innerState
    private val currentList = mutableListOf<CardBean>()
    private var page = 1
    fun loadData(isRefresh: Boolean = false) {
        if (isRefresh) innerState.value = State.Loading
        viewModelScope.launch {
            if (isRefresh) {
                page = 1
                currentList.clear()
            }
            try {
                val newItems = repositry.getPage(page)
                currentList.addAll(newItems)
                innerState.value = State.success(currentList.toList())//将currentList转换为不可变列表且为新表
                page++
            } catch (e: Exception) {
                innerState.value = State.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class State {
    data class success(val items: List<CardBean>) : State()
    object Loading : State()
    data class Error(val msg: String) : State()

}