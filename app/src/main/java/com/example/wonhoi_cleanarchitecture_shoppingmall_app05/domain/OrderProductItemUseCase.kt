package com.example.wonhoi_cleanarchitecture_shoppingmall_app05.domain

import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.entity.product.ProductEntity
import com.example.wonhoi_cleanarchitecture_shoppingmall_app05.data.repository.ProductRepository

internal class OrderProductItemUseCase(
    private val productRepository : ProductRepository
) : UseCase {

    suspend operator fun invoke(productEntity: ProductEntity) : Long {
        return productRepository.insertProductItem(productEntity)
    }

}