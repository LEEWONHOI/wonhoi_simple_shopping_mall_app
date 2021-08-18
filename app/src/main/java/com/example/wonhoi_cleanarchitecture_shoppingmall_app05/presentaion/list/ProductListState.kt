package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.presentaion.list

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity

sealed class ProductListState {

    object UnInitialized : ProductListState()

    object Loading : ProductListState()

    data class Success (
        val productList : List<ProductEntity>
            ) : ProductListState()

    object Error : ProductListState()

}