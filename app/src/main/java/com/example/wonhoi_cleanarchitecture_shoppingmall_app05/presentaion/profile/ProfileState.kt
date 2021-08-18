package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.profile

import android.net.Uri
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity

sealed class ProfileState {

    object Uninitialized : ProfileState()

    object Loding : ProfileState()

    data class Login(
        val idToken: String
    ) : ProfileState()

    sealed class Success : ProfileState() {

        data class Registered(
            val userName : String,
            val profileImageUri : Uri?,
            val productList : List<ProductEntity> = listOf()
        ) : Success()

        object NotRegistered : Success()
    }

    object Error : ProfileState()

}