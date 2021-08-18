package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

internal abstract class BaseViewModel : ViewModel() {

    abstract fun fetchData() : Job

}