package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.repository

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity

interface ProductRepository {

    suspend fun getProductList(): List<ProductEntity>

    suspend fun getLocalProductList(): List<ProductEntity>

    suspend fun getProductItem(itemId: Long): ProductEntity?

    suspend fun insertProductItem(productItem: ProductEntity): Long

    suspend fun insertProductList(productItem: List<ProductEntity>)

    suspend fun updateProductItem(productItem: ProductEntity)

    suspend fun deleteAll()

    suspend fun deleteProductItem(id: Long)

}