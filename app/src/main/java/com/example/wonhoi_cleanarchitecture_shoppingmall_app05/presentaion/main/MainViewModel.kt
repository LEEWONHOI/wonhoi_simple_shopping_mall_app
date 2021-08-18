package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class MainViewModel : BaseViewModel() {

    override fun fetchData(): Job = viewModelScope.launch {
    }

    private var _mainStateLiveData = MutableLiveData<MainState>()
    val mainStateLiveData : LiveData<MainState> = _mainStateLiveData


    fun refreshOrderList() = viewModelScope.launch {
        _mainStateLiveData.postValue(MainState.RefreshOrderList)
    }

}