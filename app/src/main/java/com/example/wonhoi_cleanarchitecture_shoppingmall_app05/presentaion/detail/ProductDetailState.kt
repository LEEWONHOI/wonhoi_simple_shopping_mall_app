package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.detail

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity

sealed class ProductDetailState {

    object UnInitialized : ProductDetailState()

    object Loading : ProductDetailState()

    data class Success (
        val productEntity: ProductEntity
            ) : ProductDetailState()

    object Order : ProductDetailState()

    object Error : ProductDetailState()

}